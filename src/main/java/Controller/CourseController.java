package Controller;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Models.Courses;
import ServiceImpl.CoursesServiceImpl;

/**
 * Endpoints:  /apiRest/courses/...
 * Mantiene el mismo estilo que StudentController.
 */
@Path("/courses")
public class CourseController {

    private final CoursesServiceImpl courseService = new CoursesServiceImpl();

    /* -------- LIST -------- */
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listCourses() {
        List<Courses> lista = courseService.listCourses();
        return Response.ok(lista).build();
    }

    /* -------- CREATE -------- */
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCourse(Courses course) {
        return courseService.createCourse(course);
    }

    /* -------- GET BY ID -------- */
    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourse(@PathParam("id") int id) {
        return courseService.getCourse(id);
    }

    /* -------- UPDATE -------- */
    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCourse(Courses course) {
        return courseService.updateCourse(course);
    }

    /* -------- DELETE -------- */
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCourse(@PathParam("id") int id) {
        return courseService.deleteCourse(id);
    }

    /* -------- COURSES BY STUDENT -------- */
    @GET
    @Path("/students/{id}/courses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoursesByStudent(@PathParam("id") int studentId) {
        return courseService.getCoursesByStudent(studentId);
    }
}
