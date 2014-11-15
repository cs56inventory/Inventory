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
	public Product(HashMap<String, String> row){

		this.setProperties(row);
	}

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
	
	private void setProperties(HashMap<String, String> row) {

		this.setProduct_upc( new Integer(row.get(Db.ProductMap.upc)) );
		this.setProduct_name( row.get(Db.ProductMap.name) );
		this.setProduct_description( row.get(Db.ProductMap.description) );
	}
	
	public HashMap<String, String> getDbpedValues(){
		HashMap<String, String> product = new HashMap<String, String>();
		product.put(Db.ProductMap.upc, new Integer(this.getProduct_upc()).toString());
		product.put(Db.ProductMap.name, this.getProduct_name());
		product.put(Db.ProductMap.description, this.getProduct_description());
		
		return product;
	}
}
