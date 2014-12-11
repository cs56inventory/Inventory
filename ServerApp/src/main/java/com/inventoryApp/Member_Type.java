package com.inventoryApp;
import java.io.Serializable;
import java.util.HashMap;


public class Member_Type implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -312219746922656692L;
	private int member_type_id;
	private String member_type_description;
	
	public Member_Type(){
		
	}
	
	public Member_Type(HashMap<String, String> row){

		this.setProperties(row);
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
	
	private void setProperties(HashMap<String, String> row){
		this.setMember_type_id(new Integer(row.get(Db.MemberTypeMap.id)));
		this.setMember_type_description(row.get(Db.MemberTypeMap.description));
	}
	
	public HashMap<String, String> getDbMappedValues(){
		HashMap<String, String> memberTypeTable = new HashMap<String, String>();
		memberTypeTable.put(Db.MemberTypeMap.id, new Integer(this.getMember_type_id()).toString());
		memberTypeTable.put(Db.MemberTypeMap.description, this.getMember_type_description());
		
		return memberTypeTable;
	}
}
