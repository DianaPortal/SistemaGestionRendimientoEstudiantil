package ServiceImpl;

import Models.StudentSummaryView;
import Models.Courses;
import connection.ConectarBD;
import dtos.StudentReportDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportServiceImpl {

    public StudentReportDTO generarReportePorId(int studentId) {
        StudentSummaryView resumen = obtenerResumenEstudiante(studentId);
        List<String> cursos = obtenerCursosPorEstudiante(studentId);
        boolean enRiesgo = resumen.getAvgGrade() < 11.0;
        List<String> recomendaciones = Collections.emptyList(); // módulo 6

        return new StudentReportDTO(
                resumen.getName(),
                resumen.getSemester(),
                resumen.getCareer(),
                resumen.getAvgGrade(),
                resumen.getTotalStudyHours(),
                cursos,
                recomendaciones,
                enRiesgo
        );
    }

    private StudentSummaryView obtenerResumenEstudiante(int studentId) {
        StudentSummaryView resumen = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConectarBD.getConexion();
            String sql = "SELECT * FROM student_summary_view WHERE student_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            rs = ps.executeQuery();

            if (rs.next()) {
                resumen = new StudentSummaryView(
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getInt("semester"),
                        rs.getString("career"),
                        rs.getDouble("avg_grade"),
                        rs.getDouble("total_study_hours")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener resumen: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, ps, rs);
        }

        return resumen;
    }

    private List<String> obtenerCursosPorEstudiante(int studentId) {
        List<String> cursos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConectarBD.getConexion();
            String sql = "SELECT c.name FROM student_courses sc " +
                         "JOIN courses c ON sc.course_id = c.id " +
                         "WHERE sc.student_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            rs = ps.executeQuery();

            while (rs.next()) {
                cursos.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener cursos: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, ps, rs);
        }

        return cursos;
    }

    private void cerrarRecursos(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
        try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
    }
}
