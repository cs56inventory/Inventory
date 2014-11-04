
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class DAL {
	// the database connection driver
		private String driver = "net.sourceforge.jtds.jdbc.Driver";
		// database connection string
		private String conn; 
		private Connection connection;
		private PreparedStatement preparedStatement;
		private ResultSet resultSet;
		private ResultSetMetaData rsmd;
		private int numCols = 0;
		private ArrayList<HashMap<String, String>> results;
		// constructor
		DAL(String q) {
    	String filePathAndName =  "sqlserver.properties";
			try{
				final Properties properties = new Properties();
				// get sql server connection url from properties file
				ClassLoader loader = Thread.currentThread().getContextClassLoader();
				InputStream resourceStream = loader.getResourceAsStream(filePathAndName);
					properties.load(resourceStream);
					resourceStream.close();
				driver = properties.getProperty("db_driver");
				conn = properties.getProperty("db_url");
				// load driver
				Class.forName(driver);
				// connect
				connection = DriverManager.getConnection(conn);
				this.preparedStatement = connection.prepareStatement(q);
				this.executeQry();
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
		}
		
		DAL(String q, ArrayList<String> parameters) {
    	String filePathAndName =  "sqlserver.properties";
			try{
				final Properties properties = new Properties();
				// get sql server connection url from properties file
				ClassLoader loader = Thread.currentThread().getContextClassLoader();
				InputStream resourceStream = loader.getResourceAsStream(filePathAndName);
					properties.load(resourceStream);
					resourceStream.close();
				driver = properties.getProperty("db_driver");
				conn = properties.getProperty("db_url");
				// load driver
				Class.forName(driver);
				// connect
				connection = DriverManager.getConnection(conn);
				this.preparedStatement = connection.prepareStatement(q);
				for(int i=0;i<=parameters.size();i++){
					this.preparedStatement.setString(i+1, parameters.get(i));
				}
				this.executeQry();
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

		}
		
		private void executeQry(){

			try {
				this.resultSet = this.preparedStatement.executeQuery();
				this.rsmd = resultSet.getMetaData();
				this.numCols = rsmd.getColumnCount();
				this.setResults();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		    try { this.resultSet.close(); } catch (Exception e) { /* ignored */ }
		    try { this.preparedStatement.close(); } catch (Exception e) { /* ignored */ }
		    try { this.connection.close(); } catch (Exception e) { /* ignored */ }
			}
		}
		
		private void setResults() throws SQLException{
			
//		   int rowCount;  
//		   int currentRow = this.resultSet.getRow();            // Get current row  
//		   rowCount = this.resultSet.last() ? this.resultSet.getRow() : 0; // Determine number of rows  
//		   if (currentRow == 0){                      // If there was no current row  
//		  	 this.resultSet.beforeFirst();
//		   }															// We want next() to go to first row  
//		   else{                                      // If there WAS a current row  
//		  	 this.resultSet.absolute(currentRow);
//		   }
			
			this.results = new ArrayList<HashMap<String, String>>();
		  try {
				while (this.resultSet.next()){
				   HashMap<String, String> row = new HashMap<String, String>(this.numCols);
				   for(int i=1; i<=this.numCols; ++i){           
				    row.put(rsmd.getColumnName(i),this.resultSet.getString(i));
				   }
				   this.results.add(row);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public ArrayList<HashMap<String, String>> getResults(){
			return this.results;
		}
		
		// get the ResultSetMetaData
		public ResultSetMetaData getRsmd(){
			return this.rsmd;
		}
		
		// get the ResultSet
		public ResultSet getRs() {
			return this.resultSet;
		}
		
		// get the number of columns in the result set
		public int getNumCols(){
			return this.numCols;
		}
}
