
public class Store_Product {
	
	private int store_id;
	private int product_upc;
	private int store_product_quantity;
	private float store_product_price;
	private int min_product_quantity;
	private int store_product_status_id;
	
	public Store_Product(){
		
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
}
