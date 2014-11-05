
public class Store {
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

	
	
}
