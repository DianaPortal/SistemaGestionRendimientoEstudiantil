package dtos;

import java.util.List;

public class StudentReportDTO {
    private String studentName;
    private int semester;
    private String career;
    private double averageGrade;
    private double totalStudyHours;
    private List<String> courseNames;
    private List<String> recommendations;
    private boolean atRisk;

    public StudentReportDTO() {
    }

    public StudentReportDTO(String studentName, int semester, String career,
                            double averageGrade, double totalStudyHours,
                            List<String> courseNames, List<String> recommendations, boolean atRisk) {
        this.studentName = studentName;
        this.semester = semester;
        this.career = career;
        this.averageGrade = averageGrade;
        this.totalStudyHours = totalStudyHours;
        this.courseNames = courseNames;
        this.recommendations = recommendations;
        this.atRisk = atRisk;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public double getTotalStudyHours() {
        return totalStudyHours;
    }

    public void setTotalStudyHours(double totalStudyHours) {
        this.totalStudyHours = totalStudyHours;
    }

    public List<String> getCourseNames() {
        return courseNames;
    }

    public void setCourseNames(List<String> courseNames) {
        this.courseNames = courseNames;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }

    public boolean isAtRisk() {
        return atRisk;
    }

    public void setAtRisk(boolean atRisk) {
        this.atRisk = atRisk;
    }
}
