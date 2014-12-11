package com.inventoryApp;
import java.io.Serializable;
import java.util.HashMap;


public class Distributor_Member implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3812964356791801866L;
	private int distributor_id;
	private int user_id;
	private int member_type_id;
	private int member_status_id;
	
	public Distributor_Member(){
		
	}
	
	public Distributor_Member(HashMap<String, String> row){

		this.setProperties(row);
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

	private void setProperties(HashMap<String, String> row){
		this.setDistributor_id(new Integer(row.get(Db.DistributorMemberMap.distributor_id)));
		this.setUser_id(new Integer(row.get(Db.DistributorMemberMap.user_id)));
		this.setMember_type_id(new Integer(row.get(Db.DistributorMemberMap.type_id)));
		this.setMember_status_id(new Integer(row.get(Db.DistributorMemberMap.status_id)));
	}
	
	public HashMap<String, String> getDbMappedValues(){
		HashMap<String, String> distributorMemberTable = new HashMap<String, String>();
		distributorMemberTable.put(Db.DistributorMemberMap.distributor_id, new Integer(this.getDistributor_id()).toString());
		distributorMemberTable.put(Db.DistributorMemberMap.user_id, new Integer(this.getUser_id()).toString());
		distributorMemberTable.put(Db.DistributorMemberMap.type_id, new Integer(this.getMember_type_id()).toString());
		distributorMemberTable.put(Db.DistributorMemberMap.status_id, new Integer(this.getMember_status_id()).toString());
		
		return distributorMemberTable;
	}

}
