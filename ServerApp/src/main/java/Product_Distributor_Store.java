
public class Product_Distributor_Store {
	private int product_upc;
	private int distributor_id;
	private int store_id;
	private float pds_product_unit_price;
	private float pds_product_shipping_fee;
	private float hours_to_deliver;
	private int min_quantity;
	private int max_quantity;
	private int pds_status_id;
	
	public Product_Distributor_Store(){
		
	}
	
	public Product_Distributor_Store(int product_upc,int distributor_id,int store_id,float pds_product_unit_price,float pds_product_shipping_fee,float hours_to_deliver,int min_quantity,int max_quantity,int pds_status_id){
		
		this.product_upc = product_upc;
		this.distributor_id = distributor_id;
		this.store_id = store_id;
		this.pds_product_unit_price = pds_product_unit_price;
		this.pds_product_shipping_fee = pds_product_shipping_fee;
		this.hours_to_deliver = hours_to_deliver;
		this.min_quantity = min_quantity;
		this.max_quantity = max_quantity;
		this.pds_status_id = pds_status_id;
	}

	public int getProduct_upc() {
		return product_upc;
	}

	public void setProduct_upc(int product_upc) {
		this.product_upc = product_upc;
	}

	public int getDistributor_id() {
		return distributor_id;
	}

	public void setDistributor_id(int distributor_id) {
		this.distributor_id = distributor_id;
	}

	public int getStore_id() {
		return store_id;
	}

	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public float getPds_product_unit_price() {
		return pds_product_unit_price;
	}

	public void setPds_product_unit_price(float pds_product_unit_price) {
		this.pds_product_unit_price = pds_product_unit_price;
	}

	public float getPds_product_shipping_fee() {
		return pds_product_shipping_fee;
	}

	public void setPds_product_shipping_fee(float pds_product_shipping_fee) {
		this.pds_product_shipping_fee = pds_product_shipping_fee;
	}

	public float getHours_to_deliver() {
		return hours_to_deliver;
	}

	public void setHours_to_deliver(float hours_to_deliver) {
		this.hours_to_deliver = hours_to_deliver;
	}

	public int getMin_quantity() {
		return min_quantity;
	}

	public void setMin_quantity(int min_quantity) {
		this.min_quantity = min_quantity;
	}

	public int getMax_quantity() {
		return max_quantity;
	}

	public void setMax_quantity(int max_quantity) {
		this.max_quantity = max_quantity;
	}

	public int getPds_status_id() {
		return pds_status_id;
	}

	public void setPds_status_id(int pds_status_id) {
		this.pds_status_id = pds_status_id;
	}
	
	
	

}
