package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.fabric.jdbc.FabricMySQLDataSource;

public class ConnectionPool
{
    private static ConnectionPool pool = null;
    private static FabricMySQLDataSource dataSource = null;
 
    private ConnectionPool() throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
//        try
//        {
//            InitialContext ic = new InitialContext();
//            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/murach");
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//    	try {
//    		String hostname = "127.0.0.1";
//            String port = "3306";
//            String database = "leyeoa";
//            // credentials to authenticate with the SQL nodes
//            String user = "leye";
//            String password = "leye";
//            // credentials to authenticate to the Fabric node
//            String fabricUsername = "leye";
//            String fabricPassword = "leye";
//
//            // setup the Fabric datasource to create connections
//            dataSource = new FabricMySQLDataSource();
//            dataSource.setServerName(hostname);
//            dataSource.setPort(Integer.valueOf(port));
//            dataSource.setDatabaseName(database);
//            dataSource.setFabricUsername(fabricUsername);
//            dataSource.setFabricPassword(fabricPassword);
//
//            // Load the driver if running under Java 5
//            if (!com.mysql.jdbc.Util.isJdbc4()) {
//                Class.forName("com.mysql.fabric.jdbc.FabricMySQLDriver");
//            }
//    	} catch(Exception e)
//    	{
//    		e.printStackTrace();
//    	}
    	Class.forName("com.mysql.jdbc.Driver").newInstance();
    }

    public static ConnectionPool getInstance()
    {
        if (pool == null)
        {
            try {
				pool = new ConnectionPool();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return pool;
    }

    public Connection getConnection()
    {
    	Connection conn = null;
    	try {
    	    conn =
    	       DriverManager.getConnection("jdbc:mysql://localhost/leyeoa?" +
    	                                   "user=leye&password=leye&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false");
    	    
    	} catch (SQLException ex) {
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
    	}
    	return conn;
    }
    
    public void freeConnection(Connection c)
    {
        try
        {
        	if (c != null) {
        		c.close();
        	}
            
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }
}