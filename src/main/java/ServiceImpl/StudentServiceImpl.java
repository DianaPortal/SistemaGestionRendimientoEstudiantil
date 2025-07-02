package ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import Models.Students;

public class StudentServiceImpl {

    private List<Students> students = new ArrayList<>();

    // Method to list all students
    public Response listStudents() {
        List<Students> studentList = students; // Fetch students from DB in production
        return Response.ok(studentList).build();
    }

    // Method to create a new student
    public Response createStudent(Students student) {
        students.add(student);  // Save student to DB in production
        return Response.status(Response.Status.CREATED).build();
    }

    // Method to get a student by ID
    public Response getStudent(int id) {
        for (Students student : students) {
            if (student.getId() == id) {
                return Response.ok(student).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
    }

    // Method to update student details
    public Response updateStudent(Students student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == student.getId()) {
                students.set(i, student); // Update student in DB in production
                return Response.status(Response.Status.OK).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Unable to update student").build();
    }

    // Method to delete a student by ID
    public Response deleteStudent(int id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == id) {
                students.remove(i); // Delete from DB in production
                return Response.status(Response.Status.NO_CONTENT).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
    }
}
