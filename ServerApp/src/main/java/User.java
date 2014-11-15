import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3432841883765302285L;
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
	
	public User(String user_email, String user_password){
		this.user_email = user_email;
		this.user_password = user_password;
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
	
	public User(HashMap<String, String> row){
		this.setProperties(row);
	}
	
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
	
	private void setProperties(HashMap<String, String> row){
		System.out.println("user id"+row.get(Db.UserMap.user_id));
		this.setUser_Id(new Integer(row.get(Db.UserMap.user_id)));
		this.setUser_first_name(row.get(Db.UserMap.first_name));
		this.setUser_last_name(row.get(Db.UserMap.last_name));
		this.setUser_email(row.get(Db.UserMap.email));
		this.setUser_password(row.get(Db.UserMap.password));
		this.setUser_created_at(new Integer(row.get(Db.UserMap.created_at)));
		this.setUser_updated_at(new Integer(row.get(Db.UserMap.updated_at)));
		this.setUser_status_id(new Integer(row.get(Db.UserMap.status_id)));
	}
	
	public HashMap<String, String> getDbpedValues(){
		HashMap<String, String> userRow = new HashMap<String, String>();
		userRow.put(Db.UserMap.user_id, new Integer(this.getUser_Id()).toString());
		userRow.put(Db.UserMap.first_name, this.getUser_first_name());
		userRow.put(Db.UserMap.last_name, this.getUser_last_name());
		userRow.put(Db.UserMap.email, this.getUser_email());
		userRow.put(Db.UserMap.password, this.getUser_password());
		userRow.put(Db.UserMap.created_at, new Integer(this.getUser_created_at()).toString());
		userRow.put(Db.UserMap.updated_at, new Integer(this.getUser_updated_at()).toString());
		userRow.put(Db.UserMap.status_id, new Integer(this.getUser_status_id()).toString());
		
		return userRow;
	}
	
}
