import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

//import DAL.userMap;


public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int user_Id;
	private String user_first_name;
	private String user_last_name;
	private String user_email;
	private String user_password;
	private int user_created_at;
	private int user_updated_at;
	private int user_status_id;

	public User(){
		
	}
	public User(int user_Id, String user_first_name, String user_last_name, String user_email, String user_password, int user_created_at, int user_updated_at, int user_status_id) {
		this.user_Id = user_Id;
		this.user_first_name = user_first_name;
		this.user_last_name = user_last_name;
		this.user_email = user_email;
		this.user_password = user_password;
		this.user_created_at = user_created_at;
		this.user_updated_at = user_updated_at;
		this.user_status_id = user_status_id;
	}
	public User(ResultSet rs){
	
		try {
			if (rs.next()) {

				this.setUser_Id(rs.getInt(DbMap.User.user_id));
				this.setUser_first_name(rs.getString(DbMap.User.first_name));
				this.setUser_last_name(rs.getString(DbMap.User.last_name));
				this.setUser_email(rs.getString(DbMap.User.email));
				this.setUser_password(rs.getString(DbMap.User.password));
				this.setUser_created_at(rs.getInt(DbMap.User.created_at));
				this.setUser_updated_at(rs.getInt(DbMap.User.updated_at));
				this.setUser_status_id(rs.getInt(DbMap.User.status_id));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}// end while
	
	public int getUser_Id() {
		return user_Id;
	}
	public void setUser_Id(int user_Id) {
		this.user_Id = user_Id;
	}
	public String getUser_first_name() {
		return user_first_name;
	}
	public void setUser_first_name(String user_first_name) {
		this.user_first_name = user_first_name;
	}
	public String getUser_last_name() {
		return user_last_name;
	}
	public void setUser_last_name(String user_last_name) {
		this.user_last_name = user_last_name;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public int getUser_created_at() {
		return user_created_at;
	}
	public void setUser_created_at(int user_created_at) {
		this.user_created_at = user_created_at;
	}
	public int getUser_updated_at() {
		return user_updated_at;
	}
	public void setUser_updated_at(int user_updated_at) {
		this.user_updated_at = user_updated_at;
	}
	public int getUser_status_id() {
		return user_status_id;
	}
	public void setUser_status_id(int user_status_id) {
		this.user_status_id = user_status_id;
	}
	
	public LinkedHashMap<String, String> getDbMappedValues(){
		LinkedHashMap<String, String> userTable = new LinkedHashMap<String, String>();
		userTable.put(DbMap.User.user_id, new Integer(this.getUser_Id()).toString());
		userTable.put(DbMap.User.first_name, this.getUser_first_name());
		userTable.put(DbMap.User.last_name, this.getUser_last_name());
		userTable.put(DbMap.User.email, this.getUser_email());
		userTable.put(DbMap.User.password, this.getUser_password());
		userTable.put(DbMap.User.created_at, new Integer(this.getUser_created_at()).toString());
		userTable.put(DbMap.User.updated_at, new Integer(this.getUser_updated_at()).toString());
		userTable.put(DbMap.User.status_id, new Integer(this.getUser_status_id()).toString());
		
		return userTable;
	}
	public boolean checkLogin(){
		
		return false;
	}
}
