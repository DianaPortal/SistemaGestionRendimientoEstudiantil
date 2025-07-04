package Controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import Models.StudyHabit;
import ServiceImpl.StudyHabitServiceImpl;
import dtos.StudySummaryDTO;

@Path("/studyhabits")
public class StudyHabitController {

	//Instancia para Acceder a la BD
	StudyHabitServiceImpl service = new StudyHabitServiceImpl();
	
	//Crea un hábito de estudio nuevo
    @POST
    @Path("/new-study-hab")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addStudyHabit(StudyHabit habit) {
        service.addStudyHabit(habit);
        
    }

    //Lista todos los hábitos de estudio de un estudiante específico
    @GET
    @Path("/students/{id}/study-habits")
    @Produces(MediaType.APPLICATION_JSON)
    public List<StudyHabit> getStudyHabits(@PathParam("id") int studentId) {
        return service.getStudyHabitsByStudentId(studentId);
    }

   

}
