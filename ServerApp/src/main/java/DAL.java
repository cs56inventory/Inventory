
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Properties;

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
	
		private int executeUpdate(){
			int results = 0;
			try {
				connection = DriverManager.getConnection(conn);
				this.preparedStatement = connection.prepareStatement(this.query);
				if(parameters.length!=0){
					for(int i=0;i<parameters.length;i++){
						this.preparedStatement.setString(i+1, parameters[i]);
					}
				}
				System.out.println("Executing Query "+this.preparedStatement);
				results = this.preparedStatement.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		    try { this.resultSet.close(); } catch (Exception e) { /* ignored */ }
		    try { this.preparedStatement.close(); } catch (Exception e) { /* ignored */ }
		    try { this.connection.close(); } catch (Exception e) { /* ignored */ }
			}
			return results;
		}
		
		private int executeInsert(){
			int results = 0;
			try {
				connection = DriverManager.getConnection(conn);
				this.preparedStatement = connection.prepareStatement(this.query,Statement.RETURN_GENERATED_KEYS);
				if(parameters.length!=0){
					for(int i=0;i<parameters.length;i++){
						this.preparedStatement.setString(i+1, parameters[i]);
					}
				}
				System.out.println("Executing Query "+this.preparedStatement);
				results = this.preparedStatement.executeUpdate();
				ResultSet generatedKeys = this.preparedStatement.getGeneratedKeys();
				if(generatedKeys.next()){
					results=generatedKeys.getInt(1);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		    try { this.resultSet.close(); } catch (Exception e) { /* ignored */ }
		    try { this.preparedStatement.close(); } catch (Exception e) { /* ignored */ }
		    try { this.connection.close(); } catch (Exception e) { /* ignored */ }
			}
			return results;
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

		protected final ArrayList<LinkedHashMap<String, String>> getQryResults(String q){
			this.query="USE "+Db.db+" "+q;
			this.executeQry();
			return this.qryResults;
		}
		
		protected final ArrayList<LinkedHashMap<String, String>> getQryResults(String q, String[] parameters){
			this.query="USE "+Db.db+" "+q;
			this.parameters=parameters;
			this.executeQry();
			return this.qryResults;
		}
		
		protected final int getUpdateResults(String q, String[] parameters){
			this.query="USE "+Db.db+" "+q;
			this.parameters=parameters;
			return this.executeUpdate();
		}
		
		protected final int getInsertResults(String q, String[] parameters){
			this.query="USE "+Db.db+" "+q;
			this.parameters=parameters;
			return this.executeInsert();
		}
		// get the ResultSetMetaData
		protected final ResultSetMetaData getRsmd(){
			return this.rsmd;
		}
		
		// get the ResultSet
		protected final ResultSet getRs() {
			return this.resultSet;
		}
		
		// get the number of columns in the result set
		protected final int getNumCols(){
			return this.numCols;
		}
		
		protected final String select(String[] columns){
			return " SELECT "+columns(columns);
		}
		
		protected final String columns(String[] columns){
			this.columns = columns;
			String clmns = columns[0];
			for(int i=1;i<columns.length;i++){
				clmns = clmns+','+columns[i];
			}
			return clmns;
		}
		
		protected final String from(String table){
			return " FROM "+table;
		}
		
		protected final String innerJoin(String table){
			return " INNER JOIN "+table;
		}
		
		protected final String on(String column1, String column2){
			return " ON "+column1+"="+column2;
		}
		
		protected final String where(String[] columns){
			String where = " WHERE "+columns[0]+"=?";
			for(int i=1;i<columns.length;i++){
				where = where+" AND "+columns[i]+"=?";
			}	
			return where;
		}
		//creates an sql select statements
		protected final String select(DbTable table){
			String select=this.select(table.columns)+this.from(table.tableName)+this.where(table.primaryKeys);
			return select;
		}
		//creates an sql select statement given a tabe name and condition keys
		protected final String select(DbTable table, String[] keys){
			String select=this.select(table.columns)+this.from(table.tableName)+this.where(keys);
			return select;
		}
		//creates an sql inner join select statement;
		protected final String select(DbTable[] table, String[][] on, String[] where){
			String[] columns=table[0].columns;

			String innerJoin="";
			for(int i=1;i<table.length;i++){
				columns=(String[]) ArrayUtils.addAll(columns,table[i].columns);
				innerJoin+=this.innerJoin(table[i].tableName)+this.on(on[i-1][0],on[i-1][1]);
			}

			String select = this.select(columns)+from(table[0].tableName)+innerJoin+this.where(where);
			return select;
		}
		protected final String[] getParameters(HashMap<String, String> valueMap, DbTable table){
			String[] parameters = new String[table.primaryKeys.length];
			for(int i=0;i<table.primaryKeys.length;i++){
				parameters[i]=valueMap.get(table.primaryKeys[i]);
			}	
			return parameters;
		}
		
		protected final int update(HashMap<String, String> valueMap, DbTable table){
			String update = "UPDATE "+table.tableName+" SET ";
			String[]parameters=new String[valueMap.size()];
			int count=0;
			for(Entry<String, String> entry: valueMap.entrySet()){
				if(count!=0){
					update+=",";
				}
				if(!Arrays.asList(table.primaryKeys).contains(entry.getKey())){
					update+=entry.getKey()+"=?";
					parameters[count]=entry.getValue();
					count++;					
				}
			}
			update = " WHERE "+table.primaryKeys[0]+"=?";
			parameters[count]=valueMap.get(table.primaryKeys[0]);
			count++;
			for(int i=1;i<table.primaryKeys.length;i++){
				update = update+" AND "+columns[i]+"=?";
				parameters[count]=valueMap.get(table.primaryKeys[i]);
				count++;
			}	

			return this.getUpdateResults(update, parameters);
		}
		
		protected final int insert(HashMap<String, String> valueMap, DbTable table){
			String insert = "INSERT INTO "+table.tableName+"(";
			String[]parameters=new String[valueMap.size()];
			int count=0;
			String valuesPlaceholder =") VALUES(";
			for(Entry<String, String> entry: valueMap.entrySet()){
				if(count!=0){
					insert+=",";
					valuesPlaceholder+=",";
				}
				if(!Arrays.asList(table.primaryKeys).contains(entry.getKey())){
					insert+=entry.getKey();
					valuesPlaceholder+="?";
					parameters[count]=entry.getValue();
					count++;					
				}
			}
			insert += valuesPlaceholder+") WHERE "+table.primaryKeys[0]+"=?";
			parameters[count]=valueMap.get(table.primaryKeys[0]);
			count++;
			for(int i=1;i<table.primaryKeys.length;i++){
				insert = insert+" AND "+columns[i]+"=?";
				parameters[count]=valueMap.get(table.primaryKeys[i]);
				count++;
			}	

			return this.getUpdateResults(insert, parameters);
		}
}
