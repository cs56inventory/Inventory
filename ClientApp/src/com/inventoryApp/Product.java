package com.inventoryApp;
import java.io.Serializable;
import java.util.HashMap;


public class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6371895414920357849L;
	private int product_upc;
	private String product_name;
	private String product_description;
	
	public Product(){
		
	}
//	public Product(HashMap<String, String> row){
//
//		this.setProperties(row);
//	}

	public Product(int product_upc, String product_name, String product_description){
		this.product_upc = product_upc;
		this.product_name = product_name;
		this.product_description = product_description;	
	}

	public int getProduct_upc() {
		return product_upc;
	}

	public void setProduct_upc(int product_upc) {
		this.product_upc = product_upc;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_description() {
		return product_description;
	}

	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}
	
//	private void setProperties(HashMap<String, String> row) {
//
//		this.setProduct_upc( new Integer(row.get(DbMap.Product.upc)) );
//		this.setProduct_name( row.get(DbMap.Product.name) );
//		this.setProduct_description( row.get(DbMap.Product.description) );
//	}
//	
//	public HashMap<String, String> getDbMappedValues(){
//		HashMap<String, String> product = new HashMap<String, String>();
//		product.put(DbMap.Product.upc, new Integer(this.getProduct_upc()).toString());
//		product.put(DbMap.Product.name, this.getProduct_name());
//		product.put(DbMap.Product.description, this.getProduct_description());
//		
//		return product;
//	}
}
