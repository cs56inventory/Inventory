import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class Db extends DAL{
	static final String db = "[imdb]";
	protected User user = new User();
	protected Store_Member store_member;
	protected Store store;
	protected Store_Product store_product;
	protected Product product;
	protected Order order;
	LinkedHashMap<Integer, Store_Product> storeProducts = new LinkedHashMap<Integer, Store_Product>();
	LinkedHashMap<Integer, Product> products = new LinkedHashMap<Integer, Product>();
	LinkedHashMap<Integer, Order> orders = new LinkedHashMap<Integer, Order>();
	LinkedHashMap<Integer, Order_Product> orderProducts = new LinkedHashMap<Integer, Order_Product>();
	protected Db(){
	}

	static final class UserMap extends DbTable{
		public UserMap(){
			this.tableName = Db.UserMap.user_table;
			this.primaryKeys= new String[]{Db.UserMap.user_id};
			this.columns = new String[]{Db.UserMap.user_id, Db.UserMap.first_name, Db.UserMap.last_name,Db.UserMap.email,Db.UserMap.password,
					Db.UserMap.created_at, Db.UserMap.updated_at,Db.UserMap.status_id,};
		}
		static final String user_table = "[user]";
		static final String user_id = Db.UserMap.user_table+"."+"[user_Id]";
		static final String first_name = Db.UserMap.user_table+"."+"[user_first_name]";
		static final String last_name = Db.UserMap.user_table+"."+"[user_last_name]";
		static final String email = Db.UserMap.user_table+"."+"[user_email]";
		static final String password = Db.UserMap.user_table+"."+"[user_password]";
		static final String created_at = Db.UserMap.user_table+"."+"[user_created_at]";
		static final String updated_at = Db.UserMap.user_table+"."+"[user_updated_at]";
		static final String status_id = Db.UserMap.user_table+"."+"[user_status_id]";
	}
	static final class StoreMemberMap extends DbTable{
		public StoreMemberMap(){
			this.tableName = Db.StoreMemberMap.store_member_table;
			this.primaryKeys = new String[]{Db.StoreMemberMap.store_id, Db.StoreMemberMap.user_id};
			this.columns = new String[]{Db.StoreMemberMap.store_id, Db.StoreMemberMap.user_id, Db.StoreMemberMap.type_id, Db.StoreMemberMap.status_id};
		}
		static final String store_member_table ="[store_member]";
		static final String store_id = Db.StoreMemberMap.store_member_table+"."+"[store_id]";
		static final String user_id = Db.StoreMemberMap.store_member_table+"."+"[user_id]";
		static final String type_id = Db.StoreMemberMap.store_member_table+"."+"[member_type_id]";
		static final String status_id = Db.StoreMemberMap.store_member_table+"."+"[member_status_id]";
	
	}	
	static final class StoreMap extends DbTable{
		public StoreMap(){
			this.tableName = Db.StoreMap.store_table;
			this.primaryKeys = new String[]{Db.StoreMap.store_id};
			this.columns = new String[]{Db.StoreMap.store_id, Db.StoreMap.name, Db.StoreMap.street_address, Db.StoreMap.city,
				Db.StoreMap.state, Db.StoreMap.zip_code, Db.StoreMap.phone_number, Db.StoreMap.status_id};	
		}
		static final String store_table = "[store]";
		static final String store_id = Db.StoreMap.store_table+"."+"[store_id]";
		static final String name = Db.StoreMap.store_table+"."+"[store_name]";
		static final String street_address = Db.StoreMap.store_table+"."+"[store_street_address]";
		static final String city = Db.StoreMap.store_table+"."+"[store_city]";
		static final String state = Db.StoreMap.store_table+"."+"[store_state]";
		static final String zip_code = Db.StoreMap.store_table+"."+"[store_zip_code]";
		static final String phone_number = Db.StoreMap.store_table+"."+"[store_phone_number]";
		static final String status_id = Db.StoreMap.store_table+"."+"[store_status_id]";

	}	
	static final class StoreProductMap extends DbTable{
		public StoreProductMap(){
			this.tableName = Db.StoreProductMap.store_product_table;
			this.primaryKeys = new String[]{Db.StoreProductMap.store_id, Db.StoreProductMap.product_upc};
			this.columns = new String[]{Db.StoreProductMap.store_id, Db.StoreProductMap.product_upc, Db.StoreProductMap.quantity, 
				Db.StoreProductMap.price,Db.StoreProductMap.min_quantity, Db.StoreProductMap.status_id};	
		}
		static final String store_product_table = "[store_product]";
		static final String store_id = Db.StoreProductMap.store_product_table+"."+"[store_id]";
		static final String product_upc = Db.StoreProductMap.store_product_table+"."+"[product_upc]";
		static final String quantity = Db.StoreProductMap.store_product_table+"."+"[store_product_quantity]";
		static final String price = Db.StoreProductMap.store_product_table+"."+"[store_product_price]";
		static final String min_quantity = Db.StoreProductMap.store_product_table+"."+"[min_product_quantity]";
		static final String status_id = Db.StoreProductMap.store_product_table+"."+"[store_product_status_id]";
	}	
	static final class ProductMap extends DbTable{
		public ProductMap(){
			this.tableName = Db.ProductMap.product_table;
			this.primaryKeys = new String[]{Db.ProductMap.upc};
			this.columns = new String[]{Db.ProductMap.upc, Db.ProductMap.name, Db.ProductMap.description};		
		}
		static final String product_table = "[product]";
		static final String upc = Db.ProductMap.product_table+"."+"[product_upc]";
		static final String name = Db.ProductMap.product_table+"."+"[product_name]";
		static final String description = Db.ProductMap.product_table+"."+"[product_description]";

	}	
	static final class OrderMap extends DbTable{
		public OrderMap(){
			this.tableName = Db.OrderMap.order_table;
			this.primaryKeys = new String[]{Db.OrderMap.order_id};
			this.columns = new String[]{Db.OrderMap.order_id, Db.OrderMap.store_id, Db.OrderMap.distributor_id, Db.OrderMap.shipping_fee,
				Db.OrderMap.total_price, Db.OrderMap.distributor_feedback,Db.OrderMap.store_feedback, Db.OrderMap.created_at,Db.OrderMap.updated_at,
				Db.OrderMap.status_id};
		}
		static final String order_table = "[order]";
		static final String order_id = Db.OrderMap.order_table+"."+"[order_id]";
		static final String store_id = Db.OrderMap.order_table+"."+"[store_id]";
		static final String distributor_id = Db.OrderMap.order_table+"."+"[distributor_id]";
		static final String shipping_fee = Db.OrderMap.order_table+"."+"[order_shipping_fee]";
		static final String total_price = Db.OrderMap.order_table+"."+"[order_total_price]";
		static final String distributor_feedback = Db.OrderMap.order_table+"."+"[distributor_feedback]";
		static final String store_feedback = Db.OrderMap.order_table+"."+"[store_feedback]";
		static final String created_at = Db.OrderMap.order_table+"."+"[order_created_at]";
		static final String updated_at = Db.OrderMap.order_table+"."+"[order_updated_at]";
		static final String status_id = Db.OrderMap.order_table+"."+"[order_status_id]";


	}	
	static final class OrderProductMap extends DbTable{
		public OrderProductMap(){
			this.tableName = Db.OrderProductMap.order_product_table;
			this.primaryKeys = new String[]{Db.OrderProductMap.order_id, Db.OrderProductMap.product_upc};
			this.columns = new String[]{Db.OrderProductMap.order_id, Db.OrderProductMap.product_upc, Db.OrderProductMap.quantity, 
				Db.OrderProductMap.totalPrice};	
		}
		static final String order_product_table = "[order_product]";
		static final String order_id = Db.OrderProductMap.order_product_table+"."+"[order_id]";
		static final String product_upc = Db.OrderProductMap.order_product_table+"."+"[product_upc]";
		static final String quantity = Db.OrderProductMap.order_product_table+"."+"[order_product_quantity]";
		static final String totalPrice = Db.OrderProductMap.order_product_table+"."+"[order_product_total_price]";

	}	
	public void getUserStoreMemberStore(){
		
		String qry = this.select(
				new DbTable[]{new Db.UserMap(),new Db.StoreMemberMap(), new Db.StoreMap()}, 
				new String[][]{new String[]{Db.UserMap.user_id,Db.StoreMemberMap.user_id},new String[]{Db.StoreMap.store_id,Db.StoreMemberMap.store_id}}, 
				new String[] {Db.UserMap.email, Db.UserMap.password
						});
		String[] parameters = new String[] { user.getUser_email(), user.getUser_password() };
		ArrayList<LinkedHashMap<String, String>> qryResults = this.getQryResults(
				qry, parameters);
		if (!qryResults.isEmpty()) {

			try{
				this.user = new User(qryResults.get(0));
				this.store_member = new Store_Member(qryResults.get(0));
				this.store = new Store(qryResults.get(0));
			}
			catch(Exception ex){
				System.out.println(ex);
				System.out.println(ex.getMessage());
			}
		}
	}
	
	public void getStoreProducts(){

		String qry = this.select(
				new DbTable[]{new Db.StoreProductMap(),new Db.ProductMap()}, 
				new String[][]{new String[]{Db.ProductMap.upc,Db.StoreProductMap.product_upc}}, 
				new String[] {Db.StoreProductMap.store_id
						});
		String[] parameters = new String[] { new Integer(store.getStore_id())
				.toString() };
		ArrayList<LinkedHashMap<String, String>> qryResults = this.getQryResults(qry, parameters);

		if (!qryResults.isEmpty()) {
			try{
				for (int i = 0; i < qryResults.size(); i++) {
					HashMap<String, String> row = qryResults.get(i);
					Store_Product storeProduct = new Store_Product(row);
					this.storeProducts.put(storeProduct.getProduct_upc(), storeProduct);
					Product product = new Product(row);
					this.products.put(product.getProduct_upc(), product);
				}				
			}
			catch(Exception ex){
				
			}
		}
	}
	
	public void getStoreOrders(){

		String qry = select(new Db.OrderMap(), new String[]{ Db.OrderMap.store_id });
		String[] parameters = new String[] { new Integer(store.getStore_id())
				.toString() };
		ArrayList<LinkedHashMap<String, String>> qryResults = this.getQryResults(qry, parameters);
		if (!qryResults.isEmpty()) {
			try{
				for (int i = 0; i < qryResults.size(); i++) {
					HashMap<String, String> row = qryResults.get(i);
					Order order = new Order(row);
					this.orders.put(order.getOrder_id(), order);
					this.getOrderProducts(order.getOrder_id());
				}		
			}
			catch(Exception ex){
				
			}
		}
	}
	public void getOrderProducts(int orderId){

		String qry = this.select(
				new DbTable[]{new Db.OrderProductMap(),new Db.ProductMap()}, 
				new String[][]{new String[]{Db.ProductMap.upc,Db.OrderProductMap.product_upc}}, 
				new String[] {Db.OrderProductMap.order_id
						});
		String[] parameters = new String[] { new Integer(orderId).toString() };
		ArrayList<LinkedHashMap<String, String>> qryResults = this.getQryResults(qry, parameters);

		if (!qryResults.isEmpty()) {
			try{
				for (int i = 0; i < qryResults.size(); i++) {
					HashMap<String, String> row = qryResults.get(i);
					Order_Product orderProduct = new Order_Product(row);
					this.orderProducts.put(orderProduct.getProduct_upc(), orderProduct);
					Product product = new Product(row);
					this.products.put(product.getProduct_upc(), product);
				}				
			}
			catch(Exception ex){
				
			}
		}
	}
}
