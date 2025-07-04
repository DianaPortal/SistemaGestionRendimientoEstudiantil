package Models;

import java.util.Date;

public class StudyHabit {
    private int id;
    private int studentId;
    private Date studyDate;
    private double hours;
    private String topic;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Date getStudyDate() {
        return studyDate;
    }
    public void setStudyDate(Date studyDate) {
        this.studyDate = studyDate;
    }

    public double getHours() {
        return hours;
    }
    public void setHours(double hours) {
        this.hours = hours;
    }

    public String getTopic() {
        return topic;
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }
}
