package ServiceImpl;

import connection.ConectarBD;
import dtos.RecommendationDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecommendationServiceImpl {

    public List<RecommendationDTO> generarRecomendaciones(int studentId) {
        List<RecommendationDTO> recomendaciones = new ArrayList<>();

        double promedio = obtenerPromedioGeneral(studentId);
        double horas = obtenerTotalHorasEstudio(studentId);
        String cursoDebil = obtenerCursoConNotaMasBaja(studentId);

        // Reglas simples de recomendación
        if (promedio < 11.0) {
            recomendaciones.add(new RecommendationDTO("Tu promedio general es bajo. Considera reforzar tus hábitos de estudio."));
        }

        if (horas < 10.0) {
            recomendaciones.add(new RecommendationDTO("Has estudiado menos de 10 horas. Intenta crear un horario semanal."));
        }

        if (cursoDebil != null) {
            recomendaciones.add(new RecommendationDTO("Dedica al menos 2 horas extra al curso: " + cursoDebil));
        }

        return recomendaciones;
    }

    private double obtenerPromedioGeneral(int studentId) {
        double promedio = 0.0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConectarBD.getConexion();
            String sql = "SELECT AVG(grade_value) as avg FROM grades WHERE student_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            rs = ps.executeQuery();

            if (rs.next()) {
                promedio = rs.getDouble("avg");
            }
        } catch (Exception e) {
            System.out.println("Error promedio: " + e.getMessage());
        } finally {
            cerrar(conn, ps, rs);
        }

        return promedio;
    }

    private double obtenerTotalHorasEstudio(int studentId) {
        double total = 0.0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConectarBD.getConexion();
            String sql = "SELECT SUM(hours) as total FROM study_habits WHERE student_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (Exception e) {
            System.out.println("Error horas estudio: " + e.getMessage());
        } finally {
            cerrar(conn, ps, rs);
        }

        return total;
    }

    private String obtenerCursoConNotaMasBaja(int studentId) {
        String nombre = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConectarBD.getConexion();
            String sql = "SELECT c.name, MIN(g.grade_value) as min_grade " +
                         "FROM grades g JOIN courses c ON g.course_id = c.id " +
                         "WHERE g.student_id = ? GROUP BY c.name ORDER BY min_grade ASC LIMIT 1";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            rs = ps.executeQuery();

            if (rs.next()) {
                nombre = rs.getString("name");
            }
        } catch (Exception e) {
            System.out.println("Error curso débil: " + e.getMessage());
        } finally {
            cerrar(conn, ps, rs);
        }

        return nombre;
    }

    private void cerrar(Connection c, PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (Exception ignored) {}
        try { if (ps != null) ps.close(); } catch (Exception ignored) {}
        try { if (c != null) c.close(); } catch (Exception ignored) {}
    }
}
