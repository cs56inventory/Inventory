package com.inventoryApp;
import java.io.Serializable;
import java.util.HashMap;


public class Distributor_Product implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6202545482567095485L;
	private int distributor_id;
	private int product_upc;
	private int distributor_product_quantity;
	private int distributor_product_status_Id;
	
	public Distributor_Product(){
		
	}
	public Distributor_Product(HashMap<String, String> row){

		this.setProperties(row);
	}

	public Distributor_Product(int distributor_id, int product_upc, int distributor_product_quantity, int distributor_product_status_Id){
		this.distributor_id = distributor_id;
		this.product_upc = product_upc;
		this.distributor_product_quantity = distributor_product_quantity;
		this.distributor_product_status_Id = distributor_product_status_Id;
	}
	
	public int getDistributor_id() {
		return distributor_id;
	}
	public void setDistributor_id(int distributor_id) {
		this.distributor_id = distributor_id;
	}
	public int getProduct_upc() {
		return product_upc;
	}
	public void setProduct_upc(int product_upc) {
		this.product_upc = product_upc;
	}
	public int getDistributor_product_quantity() {
		return distributor_product_quantity;
	}
	public void setDistributor_product_quantity(int distributor_product_quantity) {
		this.distributor_product_quantity = distributor_product_quantity;
	}
	public int getDistributor_product_status_Id() {
		return distributor_product_status_Id;
	}
	public void setDistributor_product_status_Id(int distributor_product_status_Id) {
		this.distributor_product_status_Id = distributor_product_status_Id;
	}
	private void setProperties(HashMap<String, String> row) {

		this.setDistributor_id( new Integer(row.get(Db.DistributorProductMap.distributor_id)) );
		this.setProduct_upc( new Integer(row.get(Db.DistributorProductMap.product_upc)) );
		this.setDistributor_product_quantity( new Integer(row.get(Db.DistributorProductMap.quantity)) );
		this.setDistributor_product_status_Id( new Integer(row.get(Db.DistributorProductMap.status_id)) );
	}
	
	public HashMap<String, String> getDbMappedValues(){
		HashMap<String, String> distributorProductTable = new HashMap<String, String>();
		distributorProductTable.put(Db.DistributorProductMap.distributor_id, new Integer(this.getDistributor_id()).toString());
		distributorProductTable.put(Db.DistributorProductMap.product_upc, new Integer(this.getProduct_upc()).toString());
		distributorProductTable.put(Db.DistributorProductMap.quantity, new Integer(this.getDistributor_product_quantity()).toString());
		distributorProductTable.put(Db.DistributorProductMap.status_id, new Integer(this.getDistributor_product_status_Id()).toString());

		
		return distributorProductTable;
	}
}
