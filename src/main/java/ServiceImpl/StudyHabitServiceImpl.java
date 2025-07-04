package ServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import Models.StudyHabit;
import connection.ConectarBD;
import dtos.StudySummaryDTO;

public class StudyHabitServiceImpl {


    public boolean addStudyHabit(StudyHabit habit) {
        String sql = "INSERT INTO study_habits (student_id, study_date, hours, topic) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConectarBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, habit.getStudentId());
            stmt.setDate(2, new java.sql.Date(habit.getStudyDate().getTime()));
            stmt.setDouble(3, habit.getHours());
            stmt.setString(4, habit.getTopic());

            int filasInsertadas = stmt.executeUpdate();
            return filasInsertadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	    public List<StudyHabit> getStudyHabitsByStudentId(int studentId) {
	        List<StudyHabit> habits = new ArrayList<>();
	        String sql = "SELECT * FROM study_habits WHERE student_id = ?";

	        try (Connection conn = ConectarBD.getConexion();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setInt(1, studentId);
	            ResultSet rs = stmt.executeQuery();

	            while (rs.next()) {
	                StudyHabit h = new StudyHabit();
	                h.setId(rs.getInt("id"));
	                h.setStudentId(rs.getInt("student_id"));
	                h.setStudyDate(rs.getDate("study_date"));
	                h.setHours(rs.getDouble("hours"));
	                h.setTopic(rs.getString("topic"));
	                habits.add(h);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return habits;
	    }

	    
	    
	    public StudySummaryDTO getStudySummary(int studentId) {
	        StudySummaryDTO summary = null;
	        String sql = "SELECT * FROM student_summary_view WHERE student_id = ?";

	        try (Connection conn = ConectarBD.getConexion();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setInt(1, studentId);
	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) {
	                summary = new StudySummaryDTO();
	                summary.setStudentId(rs.getInt("student_id"));
	                summary.setName(rs.getString("name"));
	                summary.setSemester(rs.getInt("semester"));
	                summary.setCareer(rs.getString("career"));
	                summary.setAvgGrade(rs.getDouble("avg_grade"));
	                summary.setTotalStudyHours(rs.getDouble("total_study_hours"));
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return summary;
	    }

	}
