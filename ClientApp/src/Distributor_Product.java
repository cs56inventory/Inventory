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

//		this.setProperties(row);
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

}
