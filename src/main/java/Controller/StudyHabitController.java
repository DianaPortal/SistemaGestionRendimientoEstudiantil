package Controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Models.StudyHabit;
import ServiceImpl.StudyHabitServiceImpl;
import dtos.StudySummaryDTO;

@Path("/studyhabits")
public class StudyHabitController {

	//Instancia para Acceder a la BD
	StudyHabitServiceImpl service = new StudyHabitServiceImpl();
	
	//Crea un hábito de estudio nuevo
	@POST
	@Path("/new-study-habits")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response addStudyHabit(StudyHabit habit) {
	    boolean registrado = service.addStudyHabit(habit);
	    if (registrado) {
	        return Response.status(Response.Status.CREATED)
	                       .entity("✅ Hábito de estudio registrado exitosamente.")
	                       .build();
	    } else {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                       .entity("❌ Error al crear el hábito de estudio.")
	                       .build();
	    }
        
    }

    //Lista todos los hábitos de estudio de un estudiante específico
    @GET
    @Path("/students/{id}/study-habits")
    @Produces(MediaType.APPLICATION_JSON)
    public List<StudyHabit> getStudyHabits(@PathParam("id") int studentId) {
        return service.getStudyHabitsByStudentId(studentId);
    }

    
    //Lista el resumen académico de un estudiante específico
    //AVG= PROMEDIO DE NOTAS
    //SUMA DE HORAS DE HÁBITO DE ESTUDIO
    @GET
    @Path("/students/{id}/study-summary")
    @Produces(MediaType.APPLICATION_JSON)
    public StudySummaryDTO getStudySummary(@PathParam("id") int studentId) {
        return service.getStudySummary(studentId);
    }

}
