package com.inventoryApp;
import java.io.Serializable;
import java.util.HashMap;

public class Store_Member implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7829676099626890025L;
	private int store_id;
	private int user_id;
	private int member_type_id;
	private int member_status_id;
	
	public Store_Member(){
		
	}
	
	public Store_Member(int user_id){
		this.user_id = user_id;
	}
	
	public Store_Member(int store_id, int user_id, int member_type_id, int member_status_id) {
		this.store_id = store_id;
		this.user_id = user_id;
		this.member_type_id = member_type_id;
		this.member_status_id = member_status_id;
	}
	
	public Store_Member(HashMap<String, String> row){
		this.setProperties(row);
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
	
	private void setProperties(HashMap<String, String> row){
		this.setUser_id(new Integer(row.get(Db.StoreMemberMap.user_id)));
		this.setStore_id(new Integer(row.get(Db.StoreMemberMap.store_id)));
		this.setMember_type_id(new Integer(row.get(Db.StoreMemberMap.type_id)));
		this.setMember_status_id(new Integer(row.get(Db.StoreMemberMap.status_id)));

	}
	
	public HashMap<String, String> getDbMappedValues(){
		HashMap<String, String> stoeMemberTable = new HashMap<String, String>();
		stoeMemberTable.put(Db.StoreMemberMap.user_id, new Integer(this.getUser_id()).toString());
		stoeMemberTable.put(Db.StoreMemberMap.store_id, new Integer(this.getStore_id()).toString());
		stoeMemberTable.put(Db.StoreMemberMap.type_id, new Integer(this.getMember_type_id()).toString());
		stoeMemberTable.put(Db.StoreMemberMap.status_id, new Integer(this.getMember_status_id()).toString());
		
		return stoeMemberTable;
	}

}
