

public final class DbMap {

	private DbMap(){
		
	}
	static class User{
		static final String table_name = "user";
		static final String user_id = "user_id";
		static final String first_name = "user_first_name";
		static final String last_name = "user_last_name";
		static final String email = "user_email";
		static final String password = "user_password";
		static final String created_at = "user_created_at";
		static final String updated_at = "user_updated_at";
		static final String status_id ="user_status_id";
	}
	static class Store_member{
		static final String table_name = "store_member";
		static final String store_id = "store_id";
		static final String user_id = "user_id";
		static final String type_id = "member_type_id";
		static final String status_id = "member_status_id";
	}	
	static class Store{
		static final String table_name = "store";
		static final String store_id = "store_id";
		static final String name = "store_name";
		static final String street_address = "store_street_address";
		static final String city = "store_city";
		static final String state = "store_state";
		static final String zip_code = "store_zip_code";
		static final String phone_number = "store_phone_number";
		static final String status_id = "store_status_id";
	}	
}
