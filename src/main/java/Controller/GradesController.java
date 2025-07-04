package Controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Models.Grades;
import ServiceImpl.GradesServiceImpl;
import dtos.GradesDTO;

@Path("/grades")
public class GradesController {

	// INSTANCIA DE CLASE
	GradesServiceImpl service = new GradesServiceImpl();

	// LISTADO DE NOTAS POR ESTUDIANTE ESPECIFICO
	@GET
	@Path("/students/{id}/grades")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGradesByStudent(@PathParam("id") int id) {
		try {
			List<Grades> lista = service.getGradesByStudent(id);
			
			// VERIFICA SI HAY NOTAS CON EL ESTUDIANTE
			if (lista.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND)
							.entity("No se encontraron notas registradas con el estudiante de ID: " + id)
							.build();
			}
			
			return Response.ok(lista).build();		
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Error al listar las notas: " + e.getMessage())
					.build();
		}
	}
	
	// REGISTRAR NUEVA NOTA
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createGrade(GradesDTO dto) {
	    try {
	        boolean success = service.createGrade(dto);

	        if (success) {
	            return Response.status(Response.Status.CREATED)
	                    .entity("Nota registrada exitosamente")
	                    .build();
	        } else {
	            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                    .entity("No se pudo registrar la nota")
	                    .build();
	        }

	    } catch (Exception e) {
	        return Response.status(Response.Status.BAD_REQUEST)
	                .entity("Error al registrar la nota: " + e.getMessage())
	                .build();
	    }
	}

	// ACTUALIZAR NOTA
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateGrade(@PathParam("id") int id, GradesDTO dto) {
	    try {
	        boolean success = service.updateGrade(id, dto);

	        if (success) {
	            return Response.ok("Nota actualizada correctamente").build();
	        } else {
	            return Response.status(Response.Status.NOT_FOUND)
	                    .entity("No se encontró la nota con ID: " + id)
	                    .build();
	        }

	    } catch (Exception e) {
	        return Response.status(Response.Status.BAD_REQUEST)
	                .entity("Error al actualizar la nota: " + e.getMessage())
	                .build();
	    }
	}

	// ELIMINAR NOTA
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteGrade(@PathParam("id") int id) {
	    try {
	        boolean success = service.deleteGrade(id);

	        if (success) {
	            return Response.ok("Nota eliminada correctamente").build();
	        } else {
	            return Response.status(Response.Status.NOT_FOUND)
	                    .entity("No se encontró la nota con ID: " + id)
	                    .build();
	        }

	    } catch (Exception e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                .entity("Error al eliminar la nota: " + e.getMessage())
	                .build();
	    }
	}
}
