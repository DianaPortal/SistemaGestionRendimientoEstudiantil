package ServiceImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import Models.Courses;
import Models.Grades;
import Models.Students;
import connection.ConectarBD;
import dtos.GradesDTO;

public class GradesServiceImpl {
	
	// CONSULTA SQL
	String sql = "";
	
	// INTERFACE
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	// LISTADO DE NOTAS POR USUARIO
	public List<Grades> getGradesByStudent(int id) {
		
		// CONSULTA ESPECIFICA
		sql = "SELECT g.id, g.grade_type, g.grade_value, g.grade_date, "
			+ "s.id AS student_id, s.name AS student_name, s.email, s.semester, s.career, "
			+ "c.id AS course_id, c.name AS course_name, c.credits " 
			+ "FROM grades AS g "
			+ "INNER JOIN students AS s ON s.id = g.student_id " 
			+ "INNER JOIN courses AS c ON c.id = g.course_id "
			+ "WHERE g.student_id = ?";
		
		// LISTADO DE NOTAS
		List<Grades> lista = new ArrayList<>();
		
		try {
			Connection con = ConectarBD.getConexion();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			while (rs.next()) {
				Grades g = new Grades();
				
				// SETEO DE CAMPOS INDEPENDIENTES
				g.setId(rs.getInt("id"));
				g.setGradeType(rs.getString("grade_type"));
				g.setGradeValue(rs.getBigDecimal("grade_value"));
				g.setGradeDate(rs.getDate("grade_date"));

				// CONTRUCCION Y SETEO DEl CAMPO "STUDENT"
				Students s = new Students();

				s.setId(rs.getInt("student_id"));
				s.setName(rs.getString("student_name"));
				s.setEmail(rs.getString("email"));
				s.setSemester(rs.getInt("semester"));
				s.setCareer(rs.getString("career"));

				g.setStudent(s);

				// CONTRUCCION Y SETEO DEl CAMPO "COURSE"
				Courses c = new Courses();

				c.setId(rs.getInt("course_id"));
				c.setName(rs.getString("course_name"));
				c.setCredits(rs.getInt("credits"));

				g.setCourse(c);
				
				// AGREGA A LA LISTA
				lista.add(g);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return lista;
	}

	// REGISTRO DE NOTA
	public boolean createGrade(GradesDTO dto) throws Exception {
		
		// CONSULTA ESPECIFICA
	    sql = "INSERT INTO grades (student_id, course_id, grade_type, grade_value, grade_date) "
	    	+ "VALUES (?, ?, ?, ?, ?)";

	    try (Connection con = ConectarBD.getConexion()) {

	        // VALIDACION DE EXISTENCIA DEL ESTUDIANTE
	        PreparedStatement psStudent = con.prepareStatement("SELECT id FROM students WHERE id = ?");
	        psStudent.setInt(1, dto.getStudent_id());
	        ResultSet rsStudent = psStudent.executeQuery();
	        
	        if (!rsStudent.next()) {
	            throw new Exception("El estudiante con ID " + dto.getStudent_id() + " no existe en la BD");
	        }

	        // VALIDACION DE EXISTENCIA DEL CURSO
	        PreparedStatement psCourse = con.prepareStatement("SELECT id FROM courses WHERE id = ?");
	        psCourse.setInt(1, dto.getCourse_id());
	        ResultSet rsCourse = psCourse.executeQuery();
	        
	        if (!rsCourse.next()) {
	            throw new Exception("El curso con ID " + dto.getCourse_id() + " no existe en la BD");
	        }

	        // REGISTRO DE NOTA
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, dto.getStudent_id());
	        ps.setInt(2, dto.getCourse_id());
	        ps.setString(3, dto.getGradeType());
	        ps.setBigDecimal(4, dto.getGradeValue());
	        ps.setDate(5, new Date(System.currentTimeMillis()));

	        int x = ps.executeUpdate();

	        return x > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new Exception("Error al registrar nota: " + e.getMessage());
	    }
	}
	
	// ACTUALIZAR NOTA
	public boolean updateGrade(int id, GradesDTO dto) throws Exception {
		
		// CONSULTA ESPECIFICA
	    sql = "UPDATE grades SET student_id = ?, course_id = ?, grade_type = ?, grade_value = ? WHERE id = ?";

	    try (Connection con = ConectarBD.getConexion()) {

	    	// VALIDACION DE EXISTENCIA DEL ESTUDIANTE
	        PreparedStatement psStudent = con.prepareStatement("SELECT id FROM students WHERE id = ?");
	        psStudent.setInt(1, dto.getStudent_id());
	        ResultSet rsStudent = psStudent.executeQuery();
	        
	        if (!rsStudent.next()) {
	            throw new Exception("El estudiante con ID " + dto.getStudent_id() + " no existe en la BD");
	        }

	        // VALIDACION DE EXISTENCIA DEL CURSO
	        PreparedStatement psCourse = con.prepareStatement("SELECT id FROM courses WHERE id = ?");
	        psCourse.setInt(1, dto.getCourse_id());
	        ResultSet rsCourse = psCourse.executeQuery();
	        
	        if (!rsCourse.next()) {
	            throw new Exception("El curso con ID " + dto.getCourse_id() + " no existe en la BD");
	        }

	        // ACTUALIZACION DE NOTA
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, dto.getStudent_id());
	        ps.setInt(2, dto.getCourse_id());
	        ps.setString(3, dto.getGradeType());
	        ps.setBigDecimal(4, dto.getGradeValue());
	        ps.setInt(5, id);

	        int x = ps.executeUpdate();
	        return x > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new Exception("Error al actualizar la nota: " + e.getMessage());
	    }
	}
	
	// ELIMINAR NOTA
	public boolean deleteGrade(int id) throws Exception {
		
		// CONSULTA ESPECIFICA
	    sql = "DELETE FROM grades WHERE id = ?";

	    try (Connection con = ConectarBD.getConexion()) {
	    	
	    	// VALIDACION DE EXISTENCIA DE LA NOTA
	        PreparedStatement psGrades = con.prepareStatement("SELECT id FROM grades WHERE id = ?");
	        psGrades.setInt(1, id);
	        ResultSet rsCourse = psGrades.executeQuery();
	        
	        if (!rsCourse.next()) {
	            throw new Exception("La nota con ID " + id + " no existe en la BD");
	        }
	    	
	        // ELIMINACION DE NOTA
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, id);

	        int x = ps.executeUpdate();
	        return x > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new Exception("Error al eliminar la nota: " + e.getMessage());
	    }
	}
}
