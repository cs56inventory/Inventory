import java.io.Serializable;
import java.util.HashMap;


public class Store_Product implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5256062572421240513L;
	private int store_id;
	private int product_upc;
	private int store_product_quantity;
	private float store_product_price;
	private int min_product_quantity;
	private int store_product_status_id;
	
	public Store_Product(){
		
	}
	public Store_Product(HashMap<String, String> row){

		this.setProperties(row);
	}

	public Store_Product(int store_id, int product_upc,	int store_product_quantity, float store_product_price, int min_product_quantity, int store_product_status_id) {
		this.store_id = store_id;
		this.product_upc = product_upc;
		this.store_product_quantity = store_product_quantity;
		this.store_product_price = store_product_price;
		this.min_product_quantity = min_product_quantity;
		this.store_product_status_id = store_product_status_id;
	}

	public int getStore_id() {
		return store_id;
	}

	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public int getProduct_upc() {
		return product_upc;
	}

	public void setProduct_upc(int product_upc) {
		this.product_upc = product_upc;
	}

	public int getStore_product_quantity() {
		return store_product_quantity;
	}

	public void setStore_product_quantity(int store_product_quantity) {
		this.store_product_quantity = store_product_quantity;
	}

	public float getStore_product_price() {
		return store_product_price;
	}

	public void setStore_product_price(float store_product_price) {
		this.store_product_price = store_product_price;
	}

	public int getMin_product_quantity() {
		return min_product_quantity;
	}

	public void setMin_product_quantity(int min_product_quantity) {
		this.min_product_quantity = min_product_quantity;
	}

	public int getStore_product_status_id() {
		return store_product_status_id;
	}

	public void setStore_product_status_id(int store_product_status_id) {
		this.store_product_status_id = store_product_status_id;
	}
	
	private void setProperties(HashMap<String, String> row) {

		this.setStore_id( new Integer(row.get(Db.StoreProductMap.store_id)) );
		this.setProduct_upc( new Integer(row.get(Db.StoreProductMap.product_upc)) );
		this.setStore_product_quantity( new Integer(row.get(Db.StoreProductMap.quantity)) );
		this.setStore_product_price( new Float(row.get(Db.StoreProductMap.price)) );
		this.setMin_product_quantity( new Integer(row.get(Db.StoreProductMap.min_quantity)) );
		this.setStore_product_status_id( new Integer(row.get(Db.StoreProductMap.status_id)) );
	}
	
	public HashMap<String, String> getDbMappedValues(){
		HashMap<String, String> stoeProductTable = new HashMap<String, String>();
		stoeProductTable.put(Db.StoreProductMap.store_id, new Integer(this.getStore_id()).toString());
		stoeProductTable.put(Db.StoreProductMap.product_upc, new Integer(this.getProduct_upc()).toString());
		stoeProductTable.put(Db.StoreProductMap.quantity, new Integer(this.getStore_product_quantity()).toString());
		stoeProductTable.put(Db.StoreProductMap.price, new Float(this.getStore_product_price()).toString());
		stoeProductTable.put(Db.StoreProductMap.min_quantity, new Integer(this.getMin_product_quantity()).toString());
		stoeProductTable.put(Db.StoreProductMap.status_id, new Integer(this.getStore_product_status_id()).toString());

		
		return stoeProductTable;
	}
}
