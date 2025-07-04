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

    //Creamos el  Metodo para el listado de todos los estudiantes
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listStudents() {
        return studentService.listStudents();
    }

    //Creamos el Metodo para el registro de estudiante
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStudent(Students student) {
        return studentService.createStudent(student);
    }

    //Creamos el  Metodo para obtener estudiante por id
    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudent(@PathParam("id") int id) {
        return studentService.getStudent(id);
    }

    //Creamos el  Metodo para actualizar estudiante
    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(Students student) {
        return studentService.updateStudent(student);
    }

    //Creamos el  Metodo para eliminar estudiante por ID
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStudent(@PathParam("id") int id) {
        return studentService.deleteStudent(id);
    }

    //Creamos el metodo registar cursos por estudiante
	@POST
	@Path("/{id}/courses")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response assignCourses(@PathParam("id") int studentId, List<Integer> courseIds) {
        return studentService.assignCoursesToStudent(studentId, courseIds);
	}
	   
 //Creamos el  Metodo para agregar un curso a un estudiante por ID
    @GET
    @Path("/{id}/courses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoursesByStudent(@PathParam("id") int id) {
        return studentService.getCoursesByStudentId(id);
    }
    
    

}