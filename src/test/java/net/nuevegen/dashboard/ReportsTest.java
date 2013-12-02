package net.nuevegen.dashboard;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportsTest {

	private static HttpServer server;
	private static WebTarget target;

	@BeforeClass
	public static void oneTimeSetUp() throws Exception {
		// start the server
		server = Dashboard.startServer();
		// create the client
		Client c = ClientBuilder.newClient();

		// uncomment the following line if you want to enable
		// support for JSON in the client (you also have to uncomment
		// dependency on jersey-media-json module in pom.xml and Main.startServer())
		// --
		//c.configuration().enable(new org.glassfish.jersey.moxy.json.MoxyJsonFeature());

		target = c.target(Dashboard.properties.getProperty(Dashboard.BASE_URI));

		String query = "INSERT INTO pm_project_event"
				+ " (`country`,`project_type`,`project_id`,`date`,`type`,`event`,`impact`,`reason`,`timelines`) VALUES"
				+ " ('RU', 'MIP', 'TEST', DATE(NOW()), 'info', 'Test', 0, 'Testing', 'PREE2E=&E2E=&UAT=&LAUNCH=')";
		Dashboard.cn_write.prepareStatement(query).executeUpdate();
	}

	@AfterClass
	public static void oneTimeTearDown() throws Exception {
		String query = "DELETE FROM pm_project_event WHERE project_id='TEST'";
		Dashboard.cn_write.prepareStatement(query).executeUpdate();

		server.stop();
	}

	/**
	 * Test to check Get Events method
	 * @throws JSONException 
	 */
	@Test
	public void testGetEvents() throws JSONException {
		JSONArray responseMsg = target.path("report/events").request(MediaType.APPLICATION_JSON).get(JSONArray.class);
		assertEquals("ru", ((JSONObject)responseMsg.get(0)).get("country"));
	}

	/**
	 * Test to check Get Events filtered by project
	 * @throws JSONException 
	 */
	@Test
	public void testGetEventsByProject() throws JSONException {
		JSONArray responseMsg = target.path("report/events/TEST").request(MediaType.APPLICATION_JSON).get(JSONArray.class);
		assertEquals(1, responseMsg.length());
	}

	/**
	 * Test to check Event update
	 * @throws JSONException 
	 */
	@Test
	public void testPostEvent() throws JSONException {
		boolean thrown = false;
		
		JSONObject event = new JSONObject();
		event.put("project_id", "TEST").put("country", "ru").put("project_type", "MIP").put("date", "2013-11-30")
			.put("type", "info").put("event", "dynamic test event").put("impact", 0).put("reason", "test")
			.put("timelines", "PE2E=2013-11-30&E2E=&UAT=&Launch=");
		
		Form form = new Form();
		@SuppressWarnings("unchecked")
		Iterator<String> iter = (Iterator<String>)event.keys();
		
		while(iter.hasNext()){
			String key = iter.next();
			form.param(key, event.getString(key));
		}
		
		try{
			Response response = target.path("report/events/TEST").request().post(Entity.form(form));
			System.out.println("Test POST response: "+ response);
			if (response.getStatus() != Status.CREATED.getStatusCode()){ 
				throw new Exception("HTTP Status is not CREATED: "+ response);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			thrown = true;
		}

		assertEquals(false, thrown);
	}
	
	/**
	 * Test to check Event update
	 * @throws JSONException 
	 */
	@Test
	public void testPutEvent() throws JSONException {
		boolean thrown = false;
		
		JSONArray responseMsg = target.path("report/events/TEST").request(MediaType.APPLICATION_JSON).get(JSONArray.class);

		JSONObject event = (JSONObject)responseMsg.get(0);
		event.put("impact", 10);
		
		Form form = new Form();
		@SuppressWarnings("unchecked")
		Iterator<String> iter = (Iterator<String>)event.keys();
		
		while(iter.hasNext()){
			String key = iter.next();
			form.param(key, event.getString(key));
		}
		
		try{
			Response response = target.path("report/events/TEST/"+ event.get("event_id")).request().put(Entity.form(form));
			System.out.println("Test PUT response: "+ response);
			if (response.getStatus() != Status.OK.getStatusCode()){ 
				throw new Exception("HTTP Status is not OK: "+ response);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			thrown = true;
		}

		assertEquals(false, thrown);
	}
	
	/**
	 * Test to check Event deletion
	 * @throws JSONException 
	 */
	@Test
	public void testDeleteEvent() throws JSONException {
		boolean thrown = false;
		
		JSONArray responseMsg = target.path("report/events/TEST").request(MediaType.APPLICATION_JSON).get(JSONArray.class);

		JSONObject event = (JSONObject)responseMsg.get(0);
		
		try{
			Response response = target.path("report/events/TEST/"+ event.get("event_id")).request(MediaType.APPLICATION_JSON).delete();
			System.out.println("Test DELETE response: "+ response);
			if (response.getStatus() != Status.OK.getStatusCode()){ 
				throw new Exception("HTTP Status is not OK: "+ response);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			thrown = true;
		}

		assertEquals(false, thrown);
	}
}
