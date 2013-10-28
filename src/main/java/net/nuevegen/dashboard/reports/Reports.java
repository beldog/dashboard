package net.nuevegen.dashboard.reports;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.nuevegen.dashboard.reports.model.Event;

/**
 * Root resource (exposed at "reports/{reportId}" path)
 */
@Path("/report")
@Produces(MediaType.APPLICATION_JSON)
public class Reports {

	@PersistenceContext(unitName="Reports", 
            type=PersistenceContextType.TRANSACTION)
	
	EntityManager entityManager;
	
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("allEvents")
    public List<Event> getAllEvents() {
    	Query query = entityManager.createNamedQuery("allEvents");
        return query.getResultList();
    }
}
