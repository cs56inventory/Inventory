
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
		private String[] columns;
		private String query; 
		private String[] parameters;
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
				if(parameters.length!=0){
					for(int i=0;i<parameters.length;i++){
						this.preparedStatement.setString(i+1, parameters[i]);
					}
				}
//				this.resultSet.getStatement()
				System.out.println("Executing Query "+this.preparedStatement);
				this.resultSet = this.preparedStatement.executeQuery();
				System.out.println("Executed Query "+this.resultSet.getStatement());
				this.rsmd = resultSet.getMetaData();
				this.numCols = rsmd.getColumnCount();
				System.out.println("Meta data "+this.rsmd);
				System.out.println("Column count "+this.numCols);
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
					
				   for(int i=0; i<this.numCols; ++i){ 
				  	 System.out.println(i+" Column name: "+this.columns[i]+" table name "+ rsmd.getTableName(i+1));
//				  	 String key = rsmd.getTableName(i)+"].["+rsmd.getColumnName(i)+"]";
				  	 String key = this.columns[i];
				  	 row.put(key,this.resultSet.getString(i+1));
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
		
		public ArrayList<LinkedHashMap<String, String>> getQryResults(String q, String[] parameters){
			this.query="USE "+DbMap.db+" "+q;
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
		
		public String select(String[] columns){
			return " SELECT "+columns(columns);
		}
		
		public String columns(String[] columns){
			this.columns = columns;
			String clmns = columns[0];
			for(int i=1;i<columns.length;i++){
				clmns = clmns+','+columns[i];
			}
			return clmns;
		}
		
		public String from(String table){
			return " FROM "+table;
		}
		
		public String innerJoin(String table){
			return " INNER JOIN "+table;
		}
		
		public String on(String column1, String column2){
			return " ON "+column1+"="+column2;
		}
		
		public String where(String[] columns){
			String where = " WHERE "+columns[0]+"=?";
			for(int i=1;i<columns.length;i++){
				where = where+" AND "+columns[i]+"=?";
			}	
			return where;
		}
}
