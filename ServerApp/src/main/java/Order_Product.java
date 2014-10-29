
public class Order_Product {
	
	private int order_id; 
	private int product_upc;  
	private int order_product_quantity; 
	private float order_product_total_price;
	
	public Order_Product(){
		
	}

	public Order_Product(int order_id, int product_upc, int order_product_quantity, float order_product_total_price){
		this.order_id = order_id;
		this.product_upc = product_upc;
		this.order_product_quantity = order_product_quantity;
		this.order_product_total_price = order_product_total_price;
		
	}
}
