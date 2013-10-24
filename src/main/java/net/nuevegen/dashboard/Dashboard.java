package net.nuevegen.dashboard;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import sun.misc.Signal;
import sun.misc.SignalHandler;


/**
 * Main Grizzly class.
 *
 */
@SuppressWarnings("restriction")
public class Dashboard {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "base_uri";

    //Server properties
    public static Properties properties;  
    
    public static void loadConfiguration() 
    		throws FileNotFoundException, IOException {
	    properties = new Properties();
	    InputStream file = new FileInputStream("config.properties");
	    
	    properties.load(file);
	    file.close();
	    
//	    for (String name : properties.stringPropertyNames()) {
//	        String value = properties.getProperty(name);
//	        System.setProperty(name, value);
//	    }
    }    
    
    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() 
    		throws FileNotFoundException, IOException{
    	//Read server properties
    	loadConfiguration();
    	
        // create a resource config that scans for JAX-RS resources and providers
        // in net.nuevegen.dashboard package
        final ResourceConfig rc = new ResourceConfig().packages("net.nuevegen.dashboard"); //$NON-NLS-1$

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(properties.getProperty(BASE_URI)), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
    	final HttpServer server = startServer();

    	System.out.println(String.format(Messages.getString("server.welcome") //$NON-NLS-1$
                + "%sapplication.wadl", properties.getProperty(BASE_URI))); //$NON-NLS-1$
 
        /*
         * Handleing system events for stop/reload
         * Source: http://stackoverflow.com/questions/9179197/what-should-a-java-program-listen-for-to-be-a-good-linux-service
         */
        Signal.handle(new Signal("HUP"), new SignalHandler() { //$NON-NLS-1$
            public void handle(Signal signal)
            {
                //reloadConfiguration();
            	System.out.println("Signal catched: "+ signal.getName() + ":"+ signal.getNumber());
            }
        });
        
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run()
            {
                System.out.println(Messages.getString("server.farewell")); //$NON-NLS-1$
            	server.stop();
            }
        }));
                
        while(true){
 
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Caught an exception while sleeping. Description: " + e.getMessage());
            }
        }
    }
}

