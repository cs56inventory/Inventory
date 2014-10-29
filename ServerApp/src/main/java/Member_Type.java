
public class Member_Type {
	
	private int member_type_id;
	private String member_type_description;
	
	public Member_Type(){
		
	}
	
	public Member_Type(int member_type_id, String member_type_description){
		this.member_type_id = member_type_id;
		this.member_type_description = member_type_description;	
	}

	public int getMember_type_id() {
		return member_type_id;
	}

	public void setMember_type_id(int member_type_id) {
		this.member_type_id = member_type_id;
	}

	public String getMember_type_description() {
		return member_type_description;
	}

	public void setMember_type_description(String member_type_description) {
		this.member_type_description = member_type_description;
	}
	
}
