import java.util.HashMap;


public class Order {
	
	private int order_id;
	private int store_id;
	private int distributor_id;
	private float order_shipping_fee;
	private float order_total_price;
	private int distributor_feedback;
	private int store_feedback;
	private int order_created_at;
	private int order_updated_at;
	private int order_status_id;
	
	
	public Order(){
		
	}
	public Order(HashMap<String, String> row){

		this.setProperties(row);
	}
	public Order(int order_id, int store_id, int distributor_id, float order_shipping_fee, float order_total_price, int distributor_feedback, int store_feedback, int order_created_at,int order_updated_at,int order_status_id ){
		
		this.order_id = order_id;
		this. store_id =  store_id;
		this.distributor_id = distributor_id;
		this.order_shipping_fee =order_shipping_fee;
		this.order_total_price = order_total_price;
		this.distributor_feedback = distributor_feedback;
		this.store_feedback = store_feedback;
		this.order_created_at = order_created_at;
		this.order_status_id = order_status_id;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getStore_id() {
		return store_id;
	}

	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public int getDistributor_id() {
		return distributor_id;
	}

	public void setDistributor_id(int distributor_id) {
		this.distributor_id = distributor_id;
	}

	public float getOrder_shipping_fee() {
		return order_shipping_fee;
	}

	public void setOrder_shipping_fee(float order_shipping_fee) {
		this.order_shipping_fee = order_shipping_fee;
	}

	public float getOrder_total_price() {
		return order_total_price;
	}

	public void setOrder_total_price(float order_total_price) {
		this.order_total_price = order_total_price;
	}

	public int getDistributor_feedback() {
		return distributor_feedback;
	}

	public void setDistributor_feedback(int distributor_feedback) {
		this.distributor_feedback = distributor_feedback;
	}

	public int getStore_feedback() {
		return store_feedback;
	}

	public void setStore_feedback(int store_feedback) {
		this.store_feedback = store_feedback;
	}

	public int getOrder_created_at() {
		return order_created_at;
	}

	public void setOrder_created_at(int order_created_at) {
		this.order_created_at = order_created_at;
	}

	public int getOrder_updated_at() {
		return order_updated_at;
	}

	public void setOrder_updated_at(int order_updated_at) {
		this.order_updated_at = order_updated_at;
	}

	public int getOrder_status_id() {
		return order_status_id;
	}

	public void setOrder_status_id(int order_status_id) {
		this.order_status_id = order_status_id;
	}
	
	private void setProperties(HashMap<String, String> row){

		this.setOrder_id(new Integer(row.get(Db.OrderMap.order_id)));
		this.setStore_id( new Integer(row.get(Db.OrderMap.store_id)) );
		this.setDistributor_id( new Integer(row.get(Db.OrderMap.distributor_id)) );
		this.setOrder_shipping_fee( new Float(row.get(Db.OrderMap.shipping_fee)) );
		this.setOrder_total_price(new Float(row.get(Db.OrderMap.total_price)));
		this.setDistributor_feedback( new Integer(row.get(Db.OrderMap.distributor_feedback)) );
		this.setStore_feedback( new Integer(row.get(Db.OrderMap.store_feedback)) );
		this.setOrder_created_at( new Integer(row.get(Db.OrderMap.created_at)) );
		this.setOrder_updated_at( new Integer(row.get(Db.OrderMap.updated_at)) );
		this.setOrder_status_id( new Integer(row.get(Db.OrderMap.status_id)) );
	}
	
	public HashMap<String, String> getDbpedValues(){
		HashMap<String, String> orderTable = new HashMap<String, String>();
		orderTable.put(Db.OrderMap.order_id, new Integer(this.getOrder_id()).toString());
		orderTable.put(Db.OrderMap.store_id, new Integer(this.getStore_id()).toString());
		orderTable.put(Db.OrderMap.distributor_id, new Integer(this.getDistributor_id()).toString());
		orderTable.put(Db.OrderMap.shipping_fee, new Float(this.getOrder_shipping_fee()).toString());
		orderTable.put(Db.OrderMap.total_price, new Float(this.getOrder_total_price()).toString());
		orderTable.put(Db.OrderMap.distributor_feedback,  new Integer(this.getDistributor_feedback()).toString());
		orderTable.put(Db.OrderMap.store_feedback,  new Integer(this.getStore_feedback()).toString());
		orderTable.put(Db.OrderMap.created_at,  new Integer(this.getOrder_created_at()).toString());
		orderTable.put(Db.OrderMap.updated_at,  new Integer(this.getOrder_updated_at()).toString());
		orderTable.put(Db.OrderMap.status_id, new Integer(this.getOrder_status_id()).toString());
		
		return orderTable;
	}
	

}
