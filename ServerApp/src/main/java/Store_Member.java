
public class Store_Member {
	
	private int store_id;
	private int user_id;
	private int member_type_id;
	private int member_status_id;
	
	public Store_Member(){
		
	}
	
	public Store_Member(int store_id, int user_id, int member_type_id, int member_status_id) {
		this.store_id = store_id;
		this.user_id = user_id;
		this.member_type_id = member_type_id;
		this.member_status_id = member_status_id;
	}

	public int getStore_id() {
		return store_id;
	}

	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getMember_type_id() {
		return member_type_id;
	}

	public void setMember_type_id(int member_type_id) {
		this.member_type_id = member_type_id;
	}

	public int getMember_status_id() {
		return member_status_id;
	}

	public void setMember_status_id(int member_status_id) {
		this.member_status_id = member_status_id;
	}


}
