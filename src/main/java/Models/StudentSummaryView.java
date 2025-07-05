package Models;

public class StudentSummaryView {
    private int studentId;
    private String name;
    private int semester;
    private String career;
    private double avgGrade;
    private double totalStudyHours;

    public StudentSummaryView() {
    }

    public StudentSummaryView(int studentId, String name, int semester, String career, double avgGrade, double totalStudyHours) {
        this.studentId = studentId;
        this.name = name;
        this.semester = semester;
        this.career = career;
        this.avgGrade = avgGrade;
        this.totalStudyHours = totalStudyHours;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(double avgGrade) {
        this.avgGrade = avgGrade;
    }

    public double getTotalStudyHours() {
        return totalStudyHours;
    }

    public void setTotalStudyHours(double totalStudyHours) {
        this.totalStudyHours = totalStudyHours;
    }
}
