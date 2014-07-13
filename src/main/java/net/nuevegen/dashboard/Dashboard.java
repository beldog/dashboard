package net.nuevegen.dashboard;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.nuevegen.dashboard.reports.Reports;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
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
	
	private static Logger logger = Logger.getLogger(Reports.class.getName());
	
	public static int MAX_DB_RETRIES = 3;
	
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "base_uri";
    public static final String DATABASE_CONNECTIONSTRING = "db_connection";
    public static final String DATABASE_USERNAME = "db_username";
    public static final String DATABASE_PASSWORD = "db_password";
    
    //Server properties
    public static Properties properties;  
    
    //DB Connection
    //TODO refactor to use JPA
    public static Connection cn_read;
    public static Connection cn_write;
    
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
     * Timeout source: http://www.cubrid.org/blog/dev-platform/understanding-jdbc-internals-and-timeout-configuration/
     */
    private static Connection setupConnection() throws ClassNotFoundException, SQLException {
    	String urlString = properties.getProperty(DATABASE_CONNECTIONSTRING);
    	String username = properties.getProperty(DATABASE_USERNAME);
    	String password = properties.getProperty(DATABASE_PASSWORD);
    	
    	Class.forName("com.mysql.jdbc.Driver");

		return DriverManager.getConnection(urlString, username, password);
    }
    
    public static Connection getConnection(boolean write) throws SQLException, Exception{
    	boolean retry = true;
    	int tries = 0;
    	Connection connection = null;
    	
    	if (write){
    		while(retry && tries < MAX_DB_RETRIES) {
	    		try{
		    		if(cn_write != null && cn_write.isValid(3)){
		    			connection = cn_write;
		    		}
		    		else{
		    			connection = setupConnection();
		    			cn_write = connection;
		    		}
		    		retry = false;
	    		}
	    		catch(SQLException e){
	    			logger.log(Level.SEVERE, "Error connection (sql error, try: "+ tries +"): "+ e.getMessage() +"/"+ e.getCause());
	    			if (tries+1 >= MAX_DB_RETRIES){
	    				throw e;
	    			}
	    		}
	    		catch(ClassNotFoundException e){
	    			retry = false;
	    			logger.log(Level.SEVERE, "Error connection (driver error): "+ e.getMessage() +"/"+ e.getCause());
	    			throw e;
	    		}
    		tries++;
    		}
    	}
    	else{
    		while(retry && tries < MAX_DB_RETRIES) {
	    		try{
		    		if(cn_read != null && cn_read.isValid(3)){
		    			connection = cn_read;
		    		}
		    		else{
		    			connection = setupConnection();
		    			connection.setReadOnly(write);
		    			cn_read = connection;
		    		}
		    		retry = false;
	    		}
	    		catch(SQLException e){
	    			logger.log(Level.SEVERE, "Error connection (sql error, try: "+ tries +"): "+ e.getMessage() +"/"+ e.getCause());
	    			if (tries+1 >= MAX_DB_RETRIES){
	    				throw e;
	    			}
	    		}
	    		catch(ClassNotFoundException e){
	    			retry = false;
	    			logger.log(Level.SEVERE, "Error connection (driver error): "+ e.getMessage() +"/"+ e.getCause());
	    			throw e;
	    		}
    		tries++;
    		}
    	}
    	
    	return connection;
    }
    
    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public static HttpServer startServer() 
    		throws FileNotFoundException, IOException, ClassNotFoundException, SQLException{
    	//Read server properties
    	loadConfiguration();
    	
    	//Set up database connectivity
    	//setupDatabase();
    	
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
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
    	
    	try{
    		final HttpServer server = startServer();
    		
    		//Adding static resources like JS and HTML
    		server.getServerConfiguration().addHttpHandler(new StaticHttpHandler("./ui"),"/ui");          
            
    		logger.log(Level.INFO, String.format(Messages.getString("server.welcome") //$NON-NLS-1$
	                + "%sapplication.wadl", properties.getProperty(BASE_URI)));
	 
	        /*
	         * Handleing system events for stop/reload
	         * Source: http://stackoverflow.com/questions/9179197/what-should-a-java-program-listen-for-to-be-a-good-linux-service
	         */
	        Signal.handle(new Signal("HUP"), new SignalHandler() { //$NON-NLS-1$
	            public void handle(Signal signal)
	            {
	                //reloadConfiguration();
	            	logger.log(Level.INFO, "Signal catched: "+ signal.getName() + ":"+ signal.getNumber());
	            }
	        });
	        
	        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	            public void run()
	            {
	                logger.log(Level.INFO, Messages.getString("server.farewell")); //$NON-NLS-1$
	            	server.stop();
	            }
	        }));	
	                
	        while(true){
	 
	            try {
	                Thread.sleep(2000);
	            } catch (InterruptedException e) {
	            	logger.log(Level.SEVERE, "Caught an exception while sleeping. Description: " + e.getMessage() +" / "+ e.getCause());
	            }
	        }
    	}
    	catch(Exception e){
    		logger.log(Level.SEVERE, "Application error: "+ e.getMessage() +" / "+ e.getCause(), e);
    		throw e;
    	}
    	finally{
    		try{
    			if(cn_read != null && !cn_read.isClosed()) cn_read.close();
    			if(cn_write != null && !cn_write.isClosed()) cn_write.close();
    		}
    		catch(SQLException e){
    			logger.log(Level.SEVERE, "Error closing connections: "+ e.getMessage() +" / "+ e.getCause(), e);
    		}
    	}
        
    }
}

