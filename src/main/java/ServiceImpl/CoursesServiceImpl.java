package ServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import Models.Courses;
import connection.ConectarBD;


public class CoursesServiceImpl {

    /* ---------- LISTAR TODOS ---------- */
    public List<Courses> listCourses() {
        List<Courses> lista = new ArrayList<>();
        String sql = "SELECT id, name, credits FROM courses";

        try (Connection con = ConectarBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Courses c = new Courses();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setCredits(rs.getInt("credits"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();   // log real en prod
        }
        return lista;
    }

    /* ---------- CREAR ---------- */
    public Response createCourse(Courses course) {
        String sql = "INSERT INTO courses (name, credits) VALUES (?, ?)";

        try (Connection con = ConectarBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, course.getName());
            ps.setInt(2, course.getCredits());
            ps.executeUpdate();

            return Response.status(Response.Status.CREATED)
                           .entity("Curso creado con exito").build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error al crear curso").build();
        }
    }

    /* ---------- OBTENER UNO ---------- */
    public Response getCourse(int id) {
        String sql = "SELECT id, name, credits FROM courses WHERE id = ?";

        try (Connection con = ConectarBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Courses c = new Courses(rs.getInt("id"),
                                            rs.getString("name"),
                                            rs.getInt("credits"));
                    return Response.ok(c).build();
                }
            }
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Course not found").build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error fetching course").build();
        }
    }

    /* ---------- ACTUALIZAR ---------- */
    public Response updateCourse(Courses course) {
        String sql = "UPDATE courses SET name = ?, credits = ? WHERE id = ?";

        try (Connection con = ConectarBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, course.getName());
            ps.setInt(2, course.getCredits());
            ps.setInt(3, course.getId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                return Response.ok("Course updated successfully").build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Course not found").build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error updating course").build();
        }
    }

    /* ---------- ELIMINAR ---------- */
    public Response deleteCourse(int id) {
        String sql = "DELETE FROM courses WHERE id = ?";

        try (Connection con = ConectarBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                return Response.ok("Course deleted successfully").build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Course not found").build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error deleting course").build();
        }
    }

    /* ---------- LISTA POR ESTUDIANTE ---------- */
    public Response getCoursesByStudent(int studentId) {
        List<Courses> lista = new ArrayList<>();
        String sql = "SELECT c.id, c.name, c.credits " +
                     "FROM student_courses sc " +
                     "JOIN courses c ON c.id = sc.course_id " +
                     "WHERE sc.student_id = ?";

        try (Connection con = ConectarBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Courses c = new Courses(rs.getInt("id"),
                                            rs.getString("name"),
                                            rs.getInt("credits"));
                    lista.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Response.ok(lista).build();
    }
}
