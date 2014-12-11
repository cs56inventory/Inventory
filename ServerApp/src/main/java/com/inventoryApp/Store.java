package com.inventoryApp;
import java.io.Serializable;
import java.util.HashMap;


public class Store implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2332704950555299287L;
	private int store_id;
	private String store_name;
	private String store_street_address;
	private String store_city;
	private String store_state;
	private String store_zip_code;
	private String store_phone_number;
	private int store_status_id;
	
	public Store(){
		
	}

	public Store(HashMap<String, String> row){

		this.setProperties(row);
	}
	
	public Store(int store_id,String store_name,String store_street_address,String store_city,String store_state,String store_zip_code,String store_phone_number,int store_status_id){
		
		 this.store_id =  store_id;
		 this.store_name = store_name;
		 this.store_street_address = store_street_address;
		 this.store_city = store_city;
		 this.store_state = store_state;
		 this.store_zip_code = store_zip_code;
		 this.store_phone_number = store_phone_number;
		 this.store_status_id = store_status_id;
		
	}

	public int getStore_id() {
		return store_id;
	}

	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getStore_street_address() {
		return store_street_address;
	}

	public void setStore_street_address(String store_street_address) {
		this.store_street_address = store_street_address;
	}

	public String getStore_city() {
		return store_city;
	}

	public void setStore_city(String store_city) {
		this.store_city = store_city;
	}

	public String getStore_state() {
		return store_state;
	}

	public void setStore_state(String store_state) {
		this.store_state = store_state;
	}

	public String getStore_zip_code() {
		return store_zip_code;
	}

	public void setStore_zip_code(String store_zip_code) {
		this.store_zip_code = store_zip_code;
	}

	public String getStore_phone_number() {
		return store_phone_number;
	}

	public void setStore_phone_number(String store_phone_number) {
		this.store_phone_number = store_phone_number;
	}

	public int getStore_status_id() {
		return store_status_id;
	}

	public void setStore_status_id(int store_status_id) {
		this.store_status_id = store_status_id;
	}
		
	private void setProperties(HashMap<String, String> row){
		this.setStore_id(new Integer(row.get(Db.StoreMap.store_id)));
		this.setStore_name(row.get(Db.StoreMap.name));
		this.setStore_street_address(row.get(Db.StoreMap.street_address));
		this.setStore_city(row.get(Db.StoreMap.city));
		this.setStore_state(row.get(Db.StoreMap.state));
		this.setStore_zip_code(row.get(Db.StoreMap.zip_code));
		this.setStore_phone_number(row.get(Db.StoreMap.phone_number));
		this.setStore_status_id(new Integer(row.get(Db.StoreMap.status_id)));
	}
	
	public HashMap<String, String> getDbMappedValues(){
		HashMap<String, String> storeTable = new HashMap<String, String>();
		storeTable.put(Db.StoreMap.store_id, new Integer(this.getStore_id()).toString());
		storeTable.put(Db.StoreMap.name, this.getStore_name());
		storeTable.put(Db.StoreMap.street_address, this.getStore_street_address());
		storeTable.put(Db.StoreMap.city, this.getStore_city());
		storeTable.put(Db.StoreMap.state, this.getStore_state());
		storeTable.put(Db.StoreMap.zip_code, this.getStore_zip_code());
		storeTable.put(Db.StoreMap.phone_number, this.getStore_name());
		storeTable.put(Db.StoreMap.status_id, new Integer(this.getStore_status_id()).toString());
		
		return storeTable;
	}
	
}
