
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Map.Entry;
import org.apache.commons.lang.ArrayUtils;


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
		private ArrayList<LinkedHashMap<String, String>> qryResults;
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
			this.qryResults = new ArrayList<LinkedHashMap<String, String>>();
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
			
		  try {
		  	
				while (this.resultSet.next()){
					LinkedHashMap<String, String> row = new LinkedHashMap<String, String>(this.numCols);
					
				   for(int i=0; i<this.numCols; ++i){ 
				  	 String key = this.columns[i];
				  	 row.put(key,this.resultSet.getString(i+1));
				   }
				   this.qryResults.add(row);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public final ArrayList<LinkedHashMap<String, String>> getQryResults(String q){
			this.query=q;
			this.executeQry();
			return this.qryResults;
		}
		
		public final ArrayList<LinkedHashMap<String, String>> getQryResults(String q, String[] parameters){
			this.query="USE "+Db.db+" "+q;
			this.parameters=parameters;
			this.executeQry();
			return this.qryResults;
		}
		// get the ResultSetMetaData
		public final ResultSetMetaData getRsmd(){
			return this.rsmd;
		}
		
		// get the ResultSet
		public final ResultSet getRs() {
			return this.resultSet;
		}
		
		// get the number of columns in the result set
		public final int getNumCols(){
			return this.numCols;
		}
		
		public final String select(String[] columns){
			return " SELECT "+columns(columns);
		}
		
		public final String columns(String[] columns){
			this.columns = columns;
			String clmns = columns[0];
			for(int i=1;i<columns.length;i++){
				clmns = clmns+','+columns[i];
			}
			return clmns;
		}
		
		public final String from(String table){
			return " FROM "+table;
		}
		
		public final String innerJoin(String table){
			return " INNER JOIN "+table;
		}
		
		public final String on(String column1, String column2){
			return " ON "+column1+"="+column2;
		}
		
		public final String where(String[] columns){
			String where = " WHERE "+columns[0]+"=?";
			for(int i=1;i<columns.length;i++){
				where = where+" AND "+columns[i]+"=?";
			}	
			return where;
		}
		//creates an sql select statements
		public final String select(DbTable table){
			String select=this.select(table.columns)+this.from(table.tableName)+this.where(table.primaryKeys);
			return select;
		}
		//creates an sql select statement given a tabe name and condition keys
		public final String select(DbTable table, String[] keys){
			String select=this.select(table.columns)+this.from(table.tableName)+this.where(keys);
			return select;
		}
		//creates an sql inner join select statement;
		public final String select(DbTable[] table, String[][] on, String[] where){
			String[] columns=table[0].columns;

			String innerJoin="";
			for(int i=1;i<table.length;i++){
				columns=(String[]) ArrayUtils.addAll(columns,table[i].columns);
				innerJoin+=this.innerJoin(table[i].tableName)+this.on(on[i-1][0],on[i-1][1]);
			}

			String select = this.select(columns)+from(table[0].tableName)+innerJoin+this.where(where);
			return select;
		}
		
		public final String update(HashMap<String, String> valueMap, DbTable table){
			String update = "UPDATE "+table.tableName+" SET ";
			int count=0;
			for(Entry<String, String> entry: valueMap.entrySet()){
				if(count!=0){
					update+=",";
				}
				if(!Arrays.asList(table.primaryKeys).contains(entry.getKey())){
					update+=entry.getKey()+"="+entry.getValue();
					count++;					
				}
			}
			update+=" WHERE ";
			for(int i=0;i<table.primaryKeys.length;i++){
				if(i!=0){
					update+=" AND ";
				}
				update+=table.primaryKeys[i]+"="+valueMap.get(table.primaryKeys[i]);
			}
			return update;
		}

}
