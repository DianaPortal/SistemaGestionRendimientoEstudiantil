package dtos;

import java.math.BigDecimal;

public class GradesDTO {

	private int student_id;
	private int course_id;
	private String gradeType;
	private BigDecimal gradeValue;
	
	public GradesDTO() {
	}
	
	public GradesDTO(int student_id, int course_id, String gradeType, BigDecimal gradeValue) {
		this.student_id = student_id;
		this.course_id = course_id;
		this.gradeType = gradeType;
		this.gradeValue = gradeValue;
	}

	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}
	public int getCourse_id() {
		return course_id;
	}
	public void setCourse_id(int course_id) {
		this.course_id = course_id;
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
}
