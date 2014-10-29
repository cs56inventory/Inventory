
public class Product {
	private int product_upc;
	private String product_name;
	private String product_description;
	
	public Product(){
		
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

}
