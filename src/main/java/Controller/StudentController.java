package Controller;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Models.Students;
import ServiceImpl.StudentServiceImpl;
@Path("/students")
public class StudentController {

    private final StudentServiceImpl studentService = new StudentServiceImpl();

    // Method to list all students
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listStudents() {
        return studentService.listStudents();
    }

    // Method to create a new student
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStudent(Students student) {
        return studentService.createStudent(student);
    }

    // Method to get a student by ID
    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudent(@PathParam("id") int id) {
        return studentService.getStudent(id);
    }

    // Method to update student details
    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(Students student) {
        return studentService.updateStudent(student);
    }

    // Method to delete a student by ID
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStudent(@PathParam("id") int id) {
        return studentService.deleteStudent(id);
    }
    
 // Method add course to student by ID
    @GET
    @Path("/{id}/courses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoursesByStudent(@PathParam("id") int id) {
        return studentService.getCoursesByStudentId(id);
    }

}