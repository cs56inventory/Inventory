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
		this.setStore_id(new Integer(row.get(DbMap.Store.store_id)));
		this.setStore_name(row.get(DbMap.Store.name));
		this.setStore_street_address(row.get(DbMap.Store.street_address));
		this.setStore_city(row.get(DbMap.Store.city));
		this.setStore_state(row.get(DbMap.Store.state));
		this.setStore_zip_code(row.get(DbMap.Store.zip_code));
		this.setStore_phone_number(row.get(DbMap.Store.phone_number));
		this.setStore_status_id(new Integer(row.get(DbMap.Store.status_id)));
	}
	
	public HashMap<String, String> getDbMappedValues(){
		HashMap<String, String> stoeTable = new HashMap<String, String>();
		stoeTable.put(DbMap.Store.store_id, new Integer(this.getStore_id()).toString());
		stoeTable.put(DbMap.Store.name, this.getStore_name());
		stoeTable.put(DbMap.Store.street_address, this.getStore_street_address());
		stoeTable.put(DbMap.Store.city, this.getStore_city());
		stoeTable.put(DbMap.Store.state, this.getStore_state());
		stoeTable.put(DbMap.Store.zip_code, this.getStore_zip_code());
		stoeTable.put(DbMap.Store.phone_number, this.getStore_name());
		stoeTable.put(DbMap.Store.status_id, new Integer(this.getStore_status_id()).toString());
		
		return stoeTable;
	}
	
}
