package ServiceImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.ws.rs.core.Response;

import Models.Students;
import connection.ConectarBD;

public class StudentServiceImpl {

    // LISTAR TODOS LOS ESTUDIANTES
    public Response listStudents() {
        List<Students> students = new ArrayList<>();
        String sql = "SELECT id, name, email, semester, career FROM students";

        try (Connection con = ConectarBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Students s = new Students();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email"));
                s.setSemester(rs.getInt("semester"));
                s.setCareer(rs.getString("career"));
                students.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al listar estudiantes").build();
        }

        return Response.ok(students).build();
    }

    // CREAR ESTUDIANTE
    public Response createStudent(Students student) {
        String checkSql = "SELECT COUNT(*) FROM students WHERE name = ? OR email = ?";
        String insertSql = "INSERT INTO students (name, email, semester, career) VALUES (?, ?, ?, ?)";

        try (Connection con = ConectarBD.getConexion();
             PreparedStatement checkPs = con.prepareStatement(checkSql)) {

            // Validar si ya existe el estudiante por nombre o correo
            checkPs.setString(1, student.getName());
            checkPs.setString(2, student.getEmail());

            try (ResultSet rs = checkPs.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return Response.status(Response.Status.CONFLICT)
                            .entity("Ya existe un estudiante con ese nombre o correo").build();
                }
            }

            // Insertar si no existe
            try (PreparedStatement insertPs = con.prepareStatement(insertSql)) {
                insertPs.setString(1, student.getName());
                insertPs.setString(2, student.getEmail());
                insertPs.setInt(3, student.getSemester());
                insertPs.setString(4, student.getCareer());

                int rows = insertPs.executeUpdate();
                if (rows > 0) {
                    return Response.status(Response.Status.CREATED).entity("Estudiante creado correctamente").build();
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo crear el estudiante").build();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error: " + e.getMessage()).build();
        }
    }

    // OBTENER UN ESTUDIANTE POR ID
    public Response getStudent(int id) {
        String sql = "SELECT id, name, email, semester, career FROM students WHERE id = ?";

        try (Connection con = ConectarBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Students s = new Students();
                    s.setId(rs.getInt("id"));
                    s.setName(rs.getString("name"));
                    s.setEmail(rs.getString("email"));
                    s.setSemester(rs.getInt("semester"));
                    s.setCareer(rs.getString("career"));
                    return Response.ok(s).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Estudiante no encontrado").build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener estudiante").build();
        }
    }

    // ACTUALIZAR ESTUDIANTE
    public Response updateStudent(Students student) {
        String sql = "UPDATE students SET name = ?, email = ?, semester = ?, career = ? WHERE id = ?";

        try (Connection con = ConectarBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setInt(3, student.getSemester());
            ps.setString(4, student.getCareer());
            ps.setInt(5, student.getId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                return Response.ok("Estudiante actualizado correctamente").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Estudiante no encontrado").build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al actualizar estudiante").build();
        }
    }

    // ELIMINAR ESTUDIANTE
    public Response deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection con = ConectarBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Estudiante no encontrado").build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al eliminar estudiante").build();
        }
    }
    
 // ASIGNAR CURSO A ESTUDIANTE
    public Response assignCoursesToStudent(int studentId, List<Integer> courseIds) {
        String insertSql = "INSERT INTO student_courses (student_id, course_id) VALUES (?, ?)";

        try (Connection con = ConectarBD.getConexion()) {
            con.setAutoCommit(false); 

            try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                for (int courseId : courseIds) {
                    ps.setInt(1, studentId);
                    ps.setInt(2, courseId);
                    ps.addBatch();
                }

                ps.executeBatch();
                con.commit();
                return Response.status(Response.Status.CREATED).entity("Cursos asignados correctamente").build();

            } catch (SQLException e) {
                con.rollback(); 
                e.printStackTrace();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al asignar cursos").build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de conexión").build();
        }
    }
    
    
    public Response getCoursesByStudentId(int studentId) {
        String sql = "SELECT s.name AS student_name, c.id AS course_id, c.name AS course_name, c.credits " +
                     "FROM students s " +
                     "JOIN student_courses sc ON s.id = sc.student_id " +
                     "JOIN courses c ON c.id = sc.course_id " +
                     "WHERE s.id = ?";

        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection con = ConectarBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("student_name", rs.getString("student_name"));
                    row.put("course_id", rs.getInt("course_id"));
                    row.put("course_name", rs.getString("course_name"));
                    row.put("credits", rs.getInt("credits"));
                    result.add(row);
                }
            }

            return Response.ok(result).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error al obtener cursos del estudiante").build();
        }
    }


    

}
