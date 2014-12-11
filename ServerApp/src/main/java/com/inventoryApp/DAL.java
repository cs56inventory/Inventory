package com.inventoryApp;

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
				connection = DriverManager.getConnection(conn);
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
			catch (SQLException e) {
				e.printStackTrace();
			}
			catch (Exception ex){
				ex.printStackTrace();
			}
		}
		
		void executeQry(Query q){

			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			
			try {
				preparedStatement = connection.prepareStatement(q.query);
				if(q.parameters!=null && q.parameters.length!=0){
					for(int i=0;i<q.parameters.length;i++){
						preparedStatement.setString(i+1, q.parameters[i]);
					}
				}
//				this.resultSet.getStatement()
				System.out.println("Executing Query "+preparedStatement);
				resultSet = preparedStatement.executeQuery();
//				System.out.println("Executed Query "+resultSet.getStatement());
				ResultSetMetaData rsmd = resultSet.getMetaData();
				int numCols = rsmd.getColumnCount();
				this.setResults(resultSet, numCols, q);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		    try { resultSet.close(); } catch (Exception e) { /* ignored */ }
		    try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
//		    try { this.connection.close(); } catch (Exception e) { /* ignored */ }
			}
		}
	
		int executeUpdate(Query q){
			int results = 0;
			PreparedStatement preparedStatement = null;
			
			try {
				
//				connection = DriverManager.getConnection(conn);
				preparedStatement = connection.prepareStatement(q.query);
				if(q.parameters!=null && q.parameters.length!=0){
					for(int i=0;i<q.parameters.length;i++){
						preparedStatement.setString(i+1, q.parameters[i]);
					}
				}
				System.out.println("Executing Query "+preparedStatement);
				results = preparedStatement.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		    try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
//		    try { this.connection.close(); } catch (Exception e) { /* ignored */ }
			}
			return results;
		}
		
		int executeInsert(Query q){
			int results = 0;
			PreparedStatement preparedStatement = null;
			try {
//				connection = DriverManager.getConnection(conn);
				preparedStatement = connection.prepareStatement(q.query,Statement.RETURN_GENERATED_KEYS);
				if(q.parameters.length!=0){
					for(int i=0;i<q.parameters.length;i++){
						preparedStatement.setString(i+1, q.parameters[i]);
					}
				}
				System.out.println("Executing Query "+preparedStatement);
				results = preparedStatement.executeUpdate();
				ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
				if(generatedKeys.next()){
					results=generatedKeys.getInt(1);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		    try { preparedStatement.close(); } catch (Exception e) { /* ignored */ }
//		    try { this.connection.close(); } catch (Exception e) { /* ignored */ }
			}
			return results;
		}
		
		private void setResults(ResultSet resultSet, int numCols, Query q ) throws SQLException{

		  try {
				while (resultSet.next()){
					LinkedHashMap<String, String> row = new LinkedHashMap<String, String>(numCols);
				   for(int i=0; i<numCols; ++i){ 
				  	 String key = q.columns[i];
				  	 row.put(key,resultSet.getString(i+1));
				   }
				   
				   q.qryResults.add(row);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		protected final int update(HashMap<String, String> valueMap, DbTable table){
			Query q = new Query(this);
			return q.update(valueMap, table);
		}
		
		protected final int insert(HashMap<String, String> valueMap, DbTable table){
			Query q = new Query(this);
			return q.insert(valueMap, table);
		}
		
}
