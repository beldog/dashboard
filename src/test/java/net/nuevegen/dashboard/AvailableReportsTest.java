package net.nuevegen.dashboard;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AvailableReportsTest {

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
     * Test to see if resource is responding with list of available reports
     */
    @Test
    public void testGetAvailableReport() {
        String responseMsg = target.path("reports").request(MediaType.APPLICATION_JSON).get(String.class);
        assertEquals("List of reports!", responseMsg);
        
        String responseMsg2 = target.path("reports/").request(MediaType.APPLICATION_JSON).get(String.class);
        assertEquals("List of reports!", responseMsg2);
    }
}
