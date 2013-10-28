package net.nuevegen.dashboard;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import net.nuevegen.dashboard.reports.model.Event;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportsTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
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
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    /**
     * Test to see if resource is responding dynamically
     */
    @Test
    public void testGetReport() {
        List<Event> responseMsg = target.path("report/allEvents").request().get(List.class);
        //assertEquals(null, responseMsg);
    }
}
