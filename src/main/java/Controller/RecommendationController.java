package Controller;

import ServiceImpl.RecommendationServiceImpl;
import dtos.RecommendationDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/students")
public class RecommendationController {

    private RecommendationServiceImpl service = new RecommendationServiceImpl();

    @GET
    @Path("/{id}/recommendations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerRecomendaciones(@PathParam("id") int studentId) {
        try {
            List<RecommendationDTO> lista = service.generarRecomendaciones(studentId);

            if (lista == null || lista.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }

            return Response.ok(lista).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error generando recomendaciones: " + e.getMessage())
                    .build();
        }
    }
}
