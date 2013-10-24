package net.nuevegen.dashboard.reports;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "reports/{reportId}" path)
 */
@Path("reports/{reportId}")
@Produces(MediaType.APPLICATION_JSON)
public class Reports {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/json" media type.
     *
     * @return String that will be returned as a application/json response.
     */
    @GET
    public String getReport(@PathParam("reportId") String reportId) {
        return "Report details: "+ reportId;
    }
}
