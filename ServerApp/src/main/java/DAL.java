
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
import java.util.LinkedHashMap;
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
		private String query; 
		private ArrayList<String> parameters;
		private ArrayList<LinkedHashMap<String, String>> results;
		// constructor
		DAL() {
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
				connection = DriverManager.getConnection(conn);
				this.preparedStatement = connection.prepareStatement(this.query);
				if(!parameters.isEmpty()){
					for(int i=0;i<parameters.size();i++){
						this.preparedStatement.setString(i+1, parameters.get(i));
					}
				}
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
			
			this.results = new ArrayList<LinkedHashMap<String, String>>();
		  try {
				while (this.resultSet.next()){
					LinkedHashMap<String, String> row = new LinkedHashMap<String, String>(this.numCols);
				   for(int i=1; i<=this.numCols; ++i){ 
				  	 String key = "["+rsmd.getSchemaName(i)+"].["+rsmd.getTableName(i)+"].["+rsmd.getColumnName(i)+"]";
				  	 row.put(key,this.resultSet.getString(i));
				   }
				   this.results.add(row);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public ArrayList<LinkedHashMap<String, String>> getQryResults(String q){
			this.query=q;
			this.executeQry();
			return this.results;
		}
		
		public ArrayList<LinkedHashMap<String, String>> getQryResults(String q, ArrayList<String> parameters){
			this.query=q;
			this.parameters=parameters;
			this.executeQry();
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
