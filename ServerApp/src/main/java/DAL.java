import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class DAL {
	// the database connection driver
		String driver = "net.sourceforge.jtds.jdbc.Driver";

		// the SQL query statement
		String query = "";
		Connection connection;
		// database connection string
		String conn; 
		ResultSet resultSet;
		ResultSetMetaData rsmd;

		// constructor
		DAL(String q) throws SQLException, ClassNotFoundException {
    	String filePathAndName =  "sqlserver.properties";
			try{
				final Properties properties = new Properties();

//				final FileInputStream in = new FileInputStream(filePathAndName);
//				properties.load(in);
//				in.close();
				
				
				
				ClassLoader loader = Thread.currentThread().getContextClassLoader();
				InputStream resourceStream = loader.getResourceAsStream(filePathAndName);
					properties.load(resourceStream);
					resourceStream.close();
				
				driver = properties.getProperty("db_driver");
				conn = properties.getProperty("db_url");
				System.out.println(driver);
				System.out.println(conn);
				// load driver
				Class.forName(driver);
				// connect
				connection = DriverManager.getConnection(conn);
				this.setQuery(q);
			}
		  catch (FileNotFoundException fnfEx)
		  {
		     System.err.println("Could not read properties from file " + filePathAndName);
		  }
		  catch (IOException ioEx)
		  {
		     System.err.println(
		        "IOException encountered while reading from " + filePathAndName);
		  }
			catch (Exception ex){
				ex.printStackTrace();
			}

		}// end dbc constructor

		// set the query string
		
		
		public void setQuery(String q) {
			this.query = q;
		}

		// get the ResultSet
		public ResultSet getRs() throws SQLException {
			// create sql statement object
			
			Statement statement = connection.createStatement();
			// create resultSet and execute query
			try{
				resultSet = statement.executeQuery(query);
			}
			catch (SQLException ex) {
			    // Exception handling stuff
			}
			finally {
		    try { resultSet.close(); } catch (Exception e) { /* ignored */ }
		    try { statement.close(); } catch (Exception e) { /* ignored */ }
		    try { connection.close(); } catch (Exception e) { /* ignored */ }
			}
			return this.resultSet;
		}

		// get the ResultSetMetaData
		public ResultSetMetaData getRsmd() throws SQLException {
			rsmd = resultSet.getMetaData();
			return this.rsmd;
		}

		// get the number of columns in the result set
		public int getNumCols() throws SQLException {
			int numCols = rsmd.getColumnCount();
			return numCols;
		}

		// close the connection
		public void closeConn() throws SQLException {
			connection.close();
		}

}
