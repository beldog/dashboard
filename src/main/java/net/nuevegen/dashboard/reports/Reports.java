package net.nuevegen.dashboard.reports;

import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import net.nuevegen.dashboard.Dashboard;
import net.nuevegen.dashboard.reports.model.Event;

/**
 * Root resource (exposed at "report/{reportId}" path)
 */
@Path("/report")
@Produces(MediaType.APPLICATION_JSON)
public class Reports {

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("events")
    public List<Event> getEvents() {
    	
    	PreparedStatement st = null;
    	ResultSet rs = null;
    	List<Event> events = new LinkedList<Event>(); 
    	try 
    	{
    		String query = "SELECT * FROM pm_project_event";

    		st = Dashboard.cn_readHeavyLoad.prepareStatement(query);
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
    		
    	} 
    	catch (Exception e) 
    	{
    		System.out.println("Error querying db: "+ e.getMessage());
    		e.printStackTrace();
    	}
    	finally{
    		try{
    			if(rs != null && !rs.isClosed()) rs.close();
    			if(st != null && !st.isClosed()) st.close();
    		}
    		catch(SQLException e){
    			e.printStackTrace();
    		}
    	}
    	
    	return events;
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("events/{id}")
    public List<Event> getEventsByProject(@PathParam("id") String id) {
    	
    	PreparedStatement st = null;
    	ResultSet rs = null;
    	List<Event> events = new LinkedList<Event>(); 
    	try 
    	{
    		String query = "SELECT * FROM pm_project_event WHERE project_id = ?";

    		st = Dashboard.cn_read.prepareStatement(query);
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
    		
    	} 
    	catch (Exception e) 
    	{
    		System.out.println("Error querying db: "+ e.getMessage());
    		e.printStackTrace();
    	}
    	finally{
    		try{
    			if(rs != null && !rs.isClosed()) rs.close();
    			if(st != null && !st.isClosed()) st.close();
    		}
    		catch(SQLException e){
    			e.printStackTrace();
    		}
    	}
    	
    	return events;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("events")
    public Response postEvent(@BeanParam Event event, @Context UriInfo ui) {
    	Response response = null;
    	
    	PreparedStatement st = null;
    	ResultSet rs = null;
    	try 
    	{
    		String query = "INSERT INTO pm_project_event"
    				+ " (`country`,`project_type`,`project_id`,`date`,`type`,`event`,`impact`,`reason`,`timelines`)"
    				+ " VALUES ("
    				+ "'" + event.getCountry() +"',"
    				+ "'" + event.getProject_type() +"',"
    				+ "'" + event.getProject_id() +"',"
    				+ "'" + event.getDate() +"',"
    				+ "'" + event.getType() +"',"
    				+ "'" + event.getEvent() +"',"
    				+ event.getImpact() +","
    				+ "'" + event.getReason() +"',"
    				+ "'" + event.getTimelines() +"')";

//    		System.out.println(query);
    		st = Dashboard.cn_read.prepareStatement(query);

    		if (st.executeUpdate() == 0){
    			response = Response.noContent().build();
    		}
    		else{
    			response = Response.created(URI.create(ui.getRequestUri() +"/"+ event.getProject_id())).build();
    		}
    	} 
    	catch (Exception e) 
    	{
    		response = Response.serverError().build();
    		System.out.println("Error querying db: "+ e.getMessage());
    		e.printStackTrace();
    	}
    	finally{
    		try{
    			if(rs != null && !rs.isClosed()) rs.close();
    			if(st != null && !st.isClosed()) st.close();
    		}
    		catch(SQLException e){
    			e.printStackTrace();
    		}
    	}
    	
    	return response;
    }
}
