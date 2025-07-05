package Controller;

import ServiceImpl.ReportServiceImpl;
import dtos.StudentReportDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/students")
public class ReportController {

    private ReportServiceImpl reportService = new ReportServiceImpl();

    @GET
    @Path("/{id}/report")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReporte(@PathParam("id") int studentId) {
        try {
            StudentReportDTO reporte = reportService.generarReportePorId(studentId);

            if (reporte == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Estudiante no encontrado.")
                               .build();
            }

            return Response.ok(reporte).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error generando el reporte: " + e.getMessage())
                           .build();
        }
    }
}
