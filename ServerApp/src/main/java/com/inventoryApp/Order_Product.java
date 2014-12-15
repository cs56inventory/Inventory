package com.inventoryApp;
import java.io.Serializable;
import java.util.HashMap;


public class Order_Product implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8638513645545860204L;
	private int order_id; 
	private int product_upc;  
	private int order_product_quantity; 
	private float order_product_total_price;
	
	public Order_Product(){
		
	}
	public Order_Product(HashMap<String, String> row){

		this.setProperties(row);
	}

	public Order_Product(int order_id, int product_upc, int order_product_quantity, float order_product_total_price){
		this.order_id = order_id;
		this.product_upc = product_upc;
		this.order_product_quantity = order_product_quantity;
		this.order_product_total_price = order_product_total_price;
		
	}
	
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public int getProduct_upc() {
		return product_upc;
	}
	public void setProduct_upc(int product_upc) {
		this.product_upc = product_upc;
	}
	public int getOrder_product_quantity() {
		return order_product_quantity;
	}
	public void setOrder_product_quantity(int order_product_quantity) {
		this.order_product_quantity = order_product_quantity;
	}
	public float getOrder_product_total_price() {
		return order_product_total_price;
	}
	public void setOrder_product_total_price(float order_product_total_price) {
		this.order_product_total_price = order_product_total_price;
	}
	private void setProperties(HashMap<String, String> row) {

		this.setOrder_id( new Integer(row.get(Db.OrderProductMap.order_id)) );
		this.setProduct_upc( new Integer(row.get(Db.OrderProductMap.product_upc)) );
		this.setOrder_product_quantity( new Integer(row.get(Db.OrderProductMap.quantity)) );
		this.setOrder_product_total_price( new Float(row.get(Db.OrderProductMap.totalPrice)) );

	}
	public HashMap<String, String> getDbMappedValues(){
		HashMap<String, String> stoeProductTable = new HashMap<String, String>();
		stoeProductTable.put(Db.OrderProductMap.order_id, new Integer(this.getOrder_id()).toString());
		stoeProductTable.put(Db.OrderProductMap.product_upc, new Integer(this.getProduct_upc()).toString());
		stoeProductTable.put(Db.OrderProductMap.quantity, new Integer(this.getOrder_product_quantity()).toString());
		stoeProductTable.put(Db.OrderProductMap.totalPrice, new Float(this.getOrder_product_total_price()).toString());
		
		return stoeProductTable;
	}
}
