package Models;

import java.math.BigDecimal;
import java.util.Date;

public class Grades {

	private int id;
	private Students student;
	private Courses course;
	private String gradeType;
	private BigDecimal gradeValue;
	private Date gradeDate;
	
	public Grades() {
	}

	public Grades(int id, Students student, Courses course, String gradeType, BigDecimal gradeValue,
			Date gradeDate) {
		this.id = id;
		this.student = student;
		this.course = course;
		this.gradeType = gradeType;
		this.gradeValue = gradeValue;
		this.gradeDate = gradeDate;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Students getStudent() {
		return student;
	}
	public void setStudent(Students student) {
		this.student = student;
	}
	public Courses getCourse() {
		return course;
	}
	public void setCourse(Courses course) {
		this.course = course;
	}
	public String getGradeType() {
		return gradeType;
	}
	public void setGradeType(String gradeType) {
		this.gradeType = gradeType;
	}
	public BigDecimal getGradeValue() {
		return gradeValue;
	}
	public void setGradeValue(BigDecimal gradeValue) {
		this.gradeValue = gradeValue;
	}
	public Date getGradeDate() {
		return gradeDate;
	}
	public void setGradeDate(Date gradeDate) {
		this.gradeDate = gradeDate;
	}
}
