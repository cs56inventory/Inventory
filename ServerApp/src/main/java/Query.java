import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.commons.lang.ArrayUtils;


public class Query {

	private DAL db;
	String[] columns;
	String query; 
	int updateResult;
	int insertResult;
	String[] parameters;
	ArrayList<LinkedHashMap<String, String>> qryResults = new ArrayList<LinkedHashMap<String, String>>();
	
	Query (DAL db){
		this.db = db;
	}
	
//	Query (String q){
//		
//		this.query = "USE "+Db.db+" "+q;
//	}
//	
//	Query (String q, String[] parameters){
//		
//		this.query = "USE "+Db.db+" "+q;
//		this.parameters = parameters;
//	}
	
	final void setQuery(String q){
		this.query = "USE "+Db.db+" "+q;
	}
	final void setParameters(String[] parameters){
		this.parameters = parameters;
	}
	
	final ArrayList<LinkedHashMap<String, String>> getQryResults(){
		if( (qryResults==null || qryResults.isEmpty()) && query!=null ){
			db.executeQry(this);
		}
		return qryResults;
	}
	
	final ArrayList<LinkedHashMap<String, String>> getQryResults(String q){
		if( (qryResults==null || qryResults.isEmpty()) && query!=null ){
			this.setQuery(q);
			db.executeQry(this);
		}
	
		return qryResults;
	}
	
	final ArrayList<LinkedHashMap<String, String>> getQryResults(String q, String[] parameters){
		if( (qryResults==null || qryResults.isEmpty()) && query!=null ){
			this.setQuery(q);
			this.setParameters(parameters);
			db.executeQry(this);
		}
	
		return qryResults;
	}
	
	protected final int getUpdateResults(String q, String[] parameters){
		this.setQuery(q);
		this.setParameters(parameters);
		this.updateResult=db.executeUpdate(this);
		return this.updateResult;
	}
	
	protected final int getInsertResults(String q, String[] parameters){
		this.setQuery(q);
		this.setParameters(parameters);
		this.insertResult=db.executeInsert(this);
		return this.insertResult;
	}

	
	final String select(String[] columns){
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
	
	final String from(String table){
		return " FROM "+table;
	}
	
	final String innerJoin(String table){
		return " INNER JOIN "+table;
	}
	
	final String on(String column1, String column2){
		return " ON "+column1+"="+column2;
	}
	
	final String where(String[] columns){
		String where = " WHERE "+columns[0]+"=?";
		for(int i=1;i<columns.length;i++){
			where = where+" AND "+columns[i]+"=?";
		}	
		return where;
	}
	//creates an sql select statements
	final String select(DbTable table, boolean parameters){
		String select;
		if(parameters){
			select=this.select(table.columns)+this.from(table.tableName)+this.where(table.primaryKeys);		
		}
		else{
			select=this.select(table.columns)+this.from(table.tableName);
		}

		return select;
	}
	//creates an sql select statement given a tabe name and condition keys
	final String select(DbTable table, String[] keys){
		String select=this.select(table.columns)+this.from(table.tableName)+this.where(keys);
		return select;
	}
	//creates an sql inner join select statement;
	final String select(DbTable[] table, String[][] on, String[] where){
		String[] columns=table[0].columns;

		String innerJoin="";
		for(int i=1;i<table.length;i++){
			columns=(String[]) ArrayUtils.addAll(columns,table[i].columns);
			innerJoin+=this.innerJoin(table[i].tableName)+this.on(on[i-1][0],on[i-1][1]);
		}

		String select = this.select(columns)+from(table[0].tableName)+innerJoin+this.where(where);
		return select;
	}
	final String[] getParameters(HashMap<String, String> valueMap, DbTable table){
		String[] parameters = new String[table.primaryKeys.length];
		for(int i=0;i<table.primaryKeys.length;i++){
			parameters[i]=valueMap.get(table.primaryKeys[i]);
		}	
		return parameters;
	}
	
	final int update(HashMap<String, String> valueMap, DbTable table){
		String update = "UPDATE "+table.tableName+" SET ";
		String[]parameters=new String[valueMap.size()];
		int count=0;
		for(Entry<String, String> entry: valueMap.entrySet()){
			if(!Arrays.asList(table.primaryKeys).contains(entry.getKey())){
				if(count!=0){
					update+=",";
				}
				update+=entry.getKey()+"=?";
				parameters[count]=entry.getValue();
				count++;					
			}
		}
		update += " WHERE "+table.primaryKeys[0]+"=?";
		parameters[count]=valueMap.get(table.primaryKeys[0]);
		count++;
		for(int i=1;i<table.primaryKeys.length;i++){
			update += " AND "+table.primaryKeys[i]+"=?";
			parameters[count]=valueMap.get(table.primaryKeys[i]);
			count++;
		}	

		return this.getUpdateResults(update, parameters);
	}
	
	final int insert(HashMap<String, String> valueMap, DbTable table){
		String insert = "INSERT INTO "+table.tableName+"(";
		String[] parameters;
		if(table.autoIncrementKeys!=null ){
			parameters=new String[valueMap.size()-table.autoIncrementKeys.length];
		}
		else{
			parameters=new String[valueMap.size()];
		}
		int count=0;
		String valuesPlaceholder =") VALUES(";
		for(Entry<String, String> entry: valueMap.entrySet()){
			if( table.autoIncrementKeys!=null ){
				if(!Arrays.asList(table.autoIncrementKeys).contains(entry.getKey())){
					if(count!=0){
						insert+=",";
						valuesPlaceholder+=",";
					}
					insert+=entry.getKey();
					valuesPlaceholder+="?";
					parameters[count]=entry.getValue();
					count++;						
				}
			}
			else{
				if(count!=0){
					insert+=",";
					valuesPlaceholder+=",";
				}
				insert+=entry.getKey();
				valuesPlaceholder+="?";
				parameters[count]=entry.getValue();
				count++;						
			}				
		}
		insert += valuesPlaceholder+") ";
		return this.getInsertResults(insert, parameters);
	}
}
