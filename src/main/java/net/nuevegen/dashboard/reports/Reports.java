package net.nuevegen.dashboard.reports;

import java.net.URI;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import net.nuevegen.dashboard.Dashboard;
import net.nuevegen.dashboard.reports.model.Delay;
import net.nuevegen.dashboard.reports.model.Event;
import net.nuevegen.dashboard.reports.model.Launch;
import net.nuevegen.dashboard.reports.model.Timeline;

/**
 * Root resource (exposed at "report/{reportId}" path)
 */
@Path("/report")
@Produces(MediaType.APPLICATION_JSON)
public class Reports {

	private static Logger logger = Logger.getLogger(Reports.class.getName());
	
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("events")
    public Response getEvents() {
    	Response response = null;
    	PreparedStatement st = null;
    	ResultSet rs = null;
    	List<Event> events = new LinkedList<Event>(); 
    	try 
    	{
    		logger.log(Level.FINE, "Requesting all Events.");
    		String query = "SELECT * FROM pm_project_event ORDER BY date DESC";

    		st = Dashboard.getConnection(false).prepareStatement(query);
    		rs = st.executeQuery();
    		
    		while (rs.next()){
    			Event event = new Event();
    			event.setCountry(rs.getString("country"));
    			event.setProject_type(rs.getString("project_type"));
    			event.setProject_id(rs.getString("project_id"));
    			event.setDate(rs.getDate("date"));
    			event.setType(rs.getString("type"));
    			event.setEvent(rs.getString("event"));
    			event.setImpact(rs.getInt("impact"));
    			event.setReason(rs.getString("reason"));
    			event.setTimelines(rs.getString("timelines"));
    			event.setAutotimestamp(rs.getTimestamp("autotimestamp"));
    			event.setEvent_id(rs.getInt("event_id"));
    			
    			events.add(event);
    		}
    		
    		GenericEntity<List<Event>> entity = new GenericEntity<List<Event>>(events) {};
    		response = Response.ok(entity).type(MediaType.APPLICATION_JSON_TYPE).build();
    		
    	} 
    	catch (Exception e) 
    	{
    		response = Response.serverError().entity("Error message: "+ e.getMessage() +"/ Cause: "+ e.getCause()).build();
    		logger.log(Level.INFO, "Error getting Events: "+ e.getMessage());
    		logger.log(Level.SEVERE, "", e);
    	}
    	finally{
    		try{
    			if(rs != null && !rs.isClosed()) rs.close();
    			if(st != null && !st.isClosed()) st.close();
    		}
    		catch(Exception e){
    			response = Response.serverError().entity("Error message: "+ e.getMessage() +"/ Cause: "+ e.getCause()).build();
    			logger.log(Level.SEVERE, "Error closing database statement and recordset: "+ e.getMessage(), e);
    		}
    	}
    	
    	return response;
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("events/{id}")
    public Response getEventsByProject(@PathParam("id") String id) {
    	Response response = null;
    	PreparedStatement st = null;
    	ResultSet rs = null;
    	List<Event> events = new LinkedList<Event>(); 
    	try 
    	{
    		String query = "SELECT * FROM pm_project_event WHERE project_id = ? ORDER BY date DESC";

    		st = Dashboard.getConnection(false).prepareStatement(query);
    		st.setString(1, id);
    		
    		rs = st.executeQuery();
    		while (rs.next()){
    			Event event = new Event();
				event.setCountry(rs.getString("country"));
				event.setProject_type(rs.getString("project_type"));
				event.setProject_id(rs.getString("project_id"));
				event.setDate(rs.getDate("date"));
				event.setType(rs.getString("type"));
				event.setEvent(rs.getString("event"));
				event.setImpact(rs.getInt("impact"));
				event.setReason(rs.getString("reason"));
				event.setTimelines(rs.getString("timelines"));
				event.setAutotimestamp(rs.getTimestamp("autotimestamp"));
				event.setEvent_id(rs.getInt("event_id"));
				
				events.add(event);
    		}
    		
    		if (events.size() > 0){
	    		GenericEntity<List<Event>> entity = new GenericEntity<List<Event>>(events) {};
	    		response = Response.ok(entity).type(MediaType.APPLICATION_JSON_TYPE).build();
    		}
    		else{
    			response = Response.noContent().build();
    		}
    		
    	} 
    	catch (Exception e) {
    		response = Response.serverError().entity("Error message: "+ e.getMessage() +"/ Cause: "+ e.getCause()).build();
    		logger.log(Level.SEVERE, "Error getting Events by project: "+ e.getMessage(), e);
    	}
    	finally{
    		try{
    			if(rs != null && !rs.isClosed()) rs.close();
    			if(st != null && !st.isClosed()) st.close();
    		}
    		catch(Exception e){
    			response = Response.serverError().entity("Error message: "+ e.getMessage() +"/ Cause: "+ e.getCause()).build();
    			logger.log(Level.SEVERE, "Error closing database statement and recordset: "+ e.getMessage(), e);
    		}
    	}
    	
    	return response;
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("launches{country : (/[a-zA-Z]+)?}")
    public Response getLaunches(@PathParam("country") String country) {
    	Response response = null;
    	PreparedStatement st = null;
    	ResultSet rs = null;
    	List<Launch> launches = new LinkedList<Launch>(); 
    	try 
    	{
    		logger.log(Level.INFO, "Quering amount of projects launched filter by country: "+ country);
    		String query = "SELECT * FROM ukint_project_amount_launches WHERE 1 ";

    		if (country != null && country.trim().length()>0){
    			query += "AND country='"+ country.replace("/", "") +"'";
    		}
    		
    		st = Dashboard.getConnection(false).prepareStatement(query);
    		rs = st.executeQuery();
    		
    		while (rs.next()){
    			Launch launch = new Launch();
    			launch.setCountry(rs.getString("country"));
    			launch.setProject_type(rs.getString("project type"));
    			launch.setDate(Date.valueOf(rs.getString("year") +"-1-1"));
    			launch.setTotal(rs.getInt("total launched"));
    			
    			launches.add(launch);
    		}
    		
    		GenericEntity<List<Launch>> entity = new GenericEntity<List<Launch>>(launches) {};
    		response = Response.ok(entity).build();
    	} 
    	catch (Exception e) {
    		response = Response.serverError().entity("Error message: "+ e.getMessage() +"/ Cause: "+ e.getCause()).build();
    		logger.log(Level.SEVERE, "Error getting Launches: "+ e.getMessage(), e);
    	}
    	finally{
    		try{
    			if(rs != null && !rs.isClosed()) rs.close();
    			if(st != null && !st.isClosed()) st.close();
    		}
    		catch(Exception e){
    			response = Response.serverError().entity("Error message: "+ e.getMessage() +"/ Cause: "+ e.getCause()).build();
    			logger.log(Level.SEVERE, "Error closing database statement and recordset: "+ e.getMessage(), e);
    		}
    	}
    	
    	return response;
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("launches{month : (/[0-9-]+)?}")
    public Response getLaunchesByMonth(@PathParam("month") String month) {
    	Response response = null;
    	PreparedStatement st = null;
    	ResultSet rs = null;
    	List<Launch> launches = new LinkedList<Launch>(); 
    	try 
    	{
    		Logger.getLogger(this.getClass().getCanonicalName()).log(Level.INFO, "Quering amount of projects launched filter by month: "+ month);
    		String query = "SELECT * FROM ukint_project_amount_launches_by_month WHERE 1 ";

    		if (month != null){
    			query += "AND month like '"+ month.replace("/", "") +"%'";
    		}
    		
    		st = Dashboard.getConnection(false).prepareStatement(query);
    		rs = st.executeQuery();
    		
    		while (rs.next()){
    			Launch launch = new Launch();
    			launch.setCountry(rs.getString("country"));
    			launch.setProject_type(rs.getString("project type"));
    			launch.setDate(Date.valueOf(rs.getString("month") +"-1"));
    			launch.setTotal(rs.getInt("total launched"));
    			
    			launches.add(launch);
    		}
    		
    		GenericEntity<List<Launch>> entity = new GenericEntity<List<Launch>>(launches) {};
    		response = Response.ok(entity).build();
    	} 
    	catch (Exception e) {
    		response = Response.serverError().entity("Error message: "+ e.getMessage() +"/ Cause: "+ e.getCause()).build();
    		logger.log(Level.SEVERE, "Error getting Launches by Month: "+ e.getMessage(), e);
    	}
    	finally{
    		try{
    			if(rs != null && !rs.isClosed()) rs.close();
    			if(st != null && !st.isClosed()) st.close();
    		}
    		catch(Exception e){
    			response = Response.serverError().entity("Error message: "+ e.getMessage() +"/ Cause: "+ e.getCause()).build();
    			logger.log(Level.SEVERE, "Error closing database statement and recordset: "+ e.getMessage(), e);
    		}
    	}
    	
    	return response;
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("delays{country : (/[a-zA-Z]+)?}")
    public Response getProjectsDelayed(@PathParam("country") String country) {
    	Response response = null;
    	PreparedStatement st = null;
    	ResultSet rs = null;
    	List<Delay> delays = new LinkedList<Delay>(); 
    	try 
    	{
    		Logger.getLogger(this.getClass().getCanonicalName()).log(Level.INFO, "Quering amount of projects delayed filter by country: "+ country);
    		String query = "SELECT * FROM ukint_project_delay_reports WHERE 1 ";

    		if (country != null && country.trim().length()>0){
    			query += "AND country='"+ country.replace("/", "") +"'";
    		}
    		
    		st = Dashboard.getConnection(false).prepareStatement(query);
    		rs = st.executeQuery();
    		
    		while (rs.next()){
    			Delay delay = new Delay();
    			delay.setCountry(rs.getString("country"));
    			delay.setMonth(rs.getString("Month"));
    			delay.setTicket(rs.getString("Ticket"));
    			delay.setProject_type(rs.getString("project type"));
    			delay.setLaunched(rs.getBoolean("Launched"));
    			delay.setRealLaunchDate(rs.getString("Real launch date"));
    			delay.setPlannedLaunchDate(rs.getString("Planned launch date"));
    			delay.setDelay(rs.getInt("Accumulated launch delay"));
    			
    			delays.add(delay);
    		}
    		
    		GenericEntity<List<Delay>> entity = new GenericEntity<List<Delay>>(delays) {};
    		response = Response.ok(entity).build();
    	} 
    	catch (Exception e) {
    		response = Response.serverError().entity("Error message: "+ e.getMessage() +"/ Cause: "+ e.getCause()).build();
    		logger.log(Level.SEVERE, "Error getting Projects delayed: "+ e.getMessage(), e);
    	}
    	finally{
    		try{
    			if(rs != null && !rs.isClosed()) rs.close();
    			if(st != null && !st.isClosed()) st.close();
    		}
    		catch(Exception e){response = Response.serverError().entity("Error message: "+ e.getMessage() +"/ Cause: "+ e.getCause()).build();response = Response.serverError().build();
    			logger.log(Level.SEVERE, "Error closing database statement and recordset: "+ e.getMessage(), e);
    		}
    	}
    	
    	return response;
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("timelines{id : (/[a-zA-Z0-9]+)?}")
    public Response getTimelinesByProject(@PathParam("id") String id) {
    	Response response = null;
    	PreparedStatement st = null;
    	ResultSet rs = null;
    	List<Timeline> timelines = new LinkedList<Timeline>(); 
    	try 
    	{
    		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    		
    		Logger.getLogger(this.getClass().getCanonicalName()).log(Level.INFO, "Quering project's timelines: "+ id);
    		String query = "SELECT * FROM ukint_project_timelines_reports WHERE 1 ";

    		if (id != null && id.trim().length()>0){
    			query += "AND ticket='"+ id.replace("/", "") +"'";
    		}
    		
    		st = Dashboard.getConnection(false).prepareStatement(query);
    		rs = st.executeQuery();
    		
    		while (rs.next()){
    			Timeline timeline = new Timeline();
    			timeline.setCountry(rs.getString("Country"));
    			timeline.setTicket(rs.getString("Ticket"));
    			timeline.setPhase(rs.getString("Phase"));
    			timeline.setStartDate(rs.getDate("Start"));
    			if(rs.getString("End") != null) timeline.setEndDate(new Date(format.parse(rs.getString("End")).getTime()));
    			timeline.setDays(rs.getInt("Days"));
    			
    			timelines.add(timeline);
    		}
    		
    		GenericEntity<List<Timeline>> entity = new GenericEntity<List<Timeline>>(timelines) {};
    		response = Response.ok(entity).build();
    	} 
    	catch (Exception e) {
    		response = Response.serverError().entity("Error message: "+ e.getMessage() +"/ Cause: "+ e.getCause()).build();
    		logger.log(Level.SEVERE, "Error getting Timelines by project: "+ e.getMessage(), e);
    	}
    	finally{
    		try{
    			if(rs != null && !rs.isClosed()) rs.close();
    			if(st != null && !st.isClosed()) st.close();
    		}
    		catch(Exception e){
    			response = Response.serverError().entity("Error message: "+ e.getMessage() +"/ Cause: "+ e.getCause()).build();
    			logger.log(Level.SEVERE, "Error closing database statement and recordset: "+ e.getMessage(), e);
    		}
    	}
    	
    	return response;
    }

    
    /* Ecent CRUD methods */
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("events/{id}")
    public Response postEvent(@BeanParam Event event, @Context UriInfo ui) {
    	Response response = null;
    	
    	PreparedStatement st = null;
    	ResultSet rs = null;
    	try 
    	{
    		String query = "INSERT INTO pm_project_event"
    				+ " (`country`,`project_type`,`project_id`,`date`,`type`,`event`,`impact`,`reason`,`timelines`)"
    				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    		st = Dashboard.getConnection(true).prepareStatement(query);
    		
    		st.setString(1, event.getCountry());
    		st.setString(2, event.getProject_type());
    		st.setString(3, event.getProject_id());
    		st.setDate(4, event.getDate());
    		st.setString(5, event.getType());
    		st.setString(6, event.getEvent());
    		st.setInt(7, event.getImpact());
    		st.setString(8, event.getReason());
    		st.setString(9, event.getTimelines());
    		
    		if (st.executeUpdate() == 0){
    			response = Response.noContent().build();
    		}
    		else{
    			response = Response.created(URI.create(ui.getRequestUri() +"/"+ event.getProject_id())).build();
    		}
    	} 
    	catch (Exception e) 
    	{
    		response = Response.serverError().entity("Error message: "+ e.getMessage() +"/ Cause: "+ e.getCause()).build();
    		logger.log(Level.SEVERE, "Error adding a new Event: "+ e.getMessage(), e);
    	}
    	finally{
    		try{
    			if(rs != null && !rs.isClosed()) rs.close();
    			if(st != null && !st.isClosed()) st.close();
    		}
    		catch(Exception e){
    			response = Response.serverError().entity("Error message: "+ e.getMessage() +"/ Cause: "+ e.getCause()).build();
    			logger.log(Level.SEVERE, "Error closing database statement and recordset: "+ e.getMessage(), e);
    		}
    	}
    	
    	return response;
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("events/{id}/{event_id}")
    public Response putEvent(@PathParam("id") String id, @PathParam("event_id") Integer event_id, @BeanParam Event event, @Context UriInfo ui) {
    	Response response = null;
    	
    	PreparedStatement st = null;
    	ResultSet rs = null;
    	try 
    	{
    		response = Response.notModified().build();
    		
    		String query = "UPDATE pm_project_event SET "
    				+ " `country`=?,"
    				+ " `project_type`=?,"
    				+ " `project_id`=?,"
    				+ " `date`=?,"
    				+ " `type`=?,"
    				+ " `event`=?,"
    				+ " `impact`=?,"
    				+ " `reason`=?,"
    				+ " `timelines`=?"
    				+ " WHERE event_id=?";

    		st = Dashboard.getConnection(true).prepareStatement(query);
    		
    		st.setString(1, event.getCountry());
    		st.setString(2, event.getProject_type());
    		st.setString(3, event.getProject_id());
    		st.setDate(4, event.getDate());
    		st.setString(5, event.getType());
    		st.setString(6, event.getEvent());
    		st.setInt(7, event.getImpact());
    		st.setString(8, event.getReason());
    		st.setString(9, event.getTimelines());
    		st.setInt(10, event_id);
    		
    		if (st.executeUpdate() != 0){
    			response = Response.ok().build();
    		}
    	} 
    	catch (Exception e) 
    	{
    		response = Response.serverError().entity("Error message: "+ e.getMessage() +"/ Cause: "+ e.getCause()).build();
    		logger.log(Level.SEVERE, "Error updating Event: "+ e.getMessage(), e);
    	}
    	finally{
    		try{
    			if(rs != null && !rs.isClosed()) rs.close();
    			if(st != null && !st.isClosed()) st.close();
    		}
    		catch(Exception e){
    			response = Response.serverError().entity("Error message: "+ e.getMessage() +"/ Cause: "+ e.getCause()).build();
    			logger.log(Level.SEVERE, "Error closing database statement and recordset: "+ e.getMessage(), e);
    		}
    	}
    	System.out.println("PUT call response: "+ response);
    	return response;
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("events/{id}/{event_id}")
    public Response deleteEvent(@PathParam("id") String id, @PathParam("event_id") Integer event_id) {
    	Response response = null;
    	
    	PreparedStatement st = null;
    	ResultSet rs = null;
    	try 
    	{
    		response = Response.notModified().build();
    		
    		String query = "DELETE FROM pm_project_event WHERE event_id=?";

    		st = Dashboard.getConnection(true).prepareStatement(query);
    		
    		st.setInt(1, event_id);
    		
    		if (st.executeUpdate() != 0){
    			response = Response.ok().build();
    		}
    	} 
    	catch (Exception e) 
    	{
    		response = Response.serverError().entity("Error message: "+ e.getMessage() +"/ Cause: "+ e.getCause()).build();
    		logger.log(Level.SEVERE, "Error deleting the Event: "+ e.getMessage(), e);
    	}
    	finally{
    		try{
    			if(rs != null && !rs.isClosed()) rs.close();
    			if(st != null && !st.isClosed()) st.close();
    		}
    		catch(Exception e){
    			response = Response.serverError().entity("Error message: "+ e.getMessage() +"/ Cause: "+ e.getCause()).build();
    			logger.log(Level.SEVERE, "Error closing database statement and recordset: "+ e.getMessage(), e);
    		}
    	}
    	
    	return response;
    }
}
