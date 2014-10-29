
public class Distributor_Member {
	
	private int distributor_id;
	private int user_id;
	private int member_type_id;
	private int member_status_id;
	
	public Distributor_Member(){
		
	}
	
	public Distributor_Member(int distributor_id, int user_id, int member_type_id, int member_status_id){
		
		this.distributor_id = distributor_id;
		this.user_id = user_id;
		this.member_type_id = member_type_id;
		this.member_status_id = member_status_id;	
	}
	
	public int getDistributor_id() {
		return distributor_id;
	}
	public void setDistributor_id(int distributor_id) {
		this.distributor_id = distributor_id;
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
