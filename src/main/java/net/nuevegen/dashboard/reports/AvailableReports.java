package net.nuevegen.dashboard.reports;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "reports/{reportId}" path)
 */
@Path("reports")
@Produces(MediaType.APPLICATION_JSON)
public class AvailableReports {

    @GET
    public String getAvailableReports() {
        return "List of reports!";
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAvailableReportsAsTextPlain(){
    	return "Please use "+ MediaType.APPLICATION_JSON +" content-type.";
    }
}
