

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
	
}
