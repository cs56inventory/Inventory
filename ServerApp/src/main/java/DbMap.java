

public final class DbMap {
	static final String db = "[imdb]";
	private DbMap(){
		
	}
	static class User{
		static final String user_table = "[user]";
		static final String user_id = DbMap.User.user_table+"."+"[user_Id]";
		static final String first_name = DbMap.User.user_table+"."+"[user_first_name]";
		static final String last_name = DbMap.User.user_table+"."+"[user_last_name]";
		static final String email = DbMap.User.user_table+"."+"[user_email]";
		static final String password = DbMap.User.user_table+"."+"[user_password]";
		static final String created_at = DbMap.User.user_table+"."+"[user_created_at]";
		static final String updated_at = DbMap.User.user_table+"."+"[user_updated_at]";
		static final String status_id = DbMap.User.user_table+"."+"[user_status_id]";
	}
	static class Store_member{
		static final String store_member_table ="[store_member]";
		static final String store_id = DbMap.Store_member.store_member_table+"."+"[store_id]";
		static final String user_id = DbMap.Store_member.store_member_table+"."+"[user_id]";
		static final String type_id = DbMap.Store_member.store_member_table+"."+"[member_type_id]";
		static final String status_id = DbMap.Store_member.store_member_table+"."+"[member_status_id]";
	}	
	static class Store{
		static final String store_table = "[store]";
		static final String store_id = DbMap.Store.store_table+"."+"[store_id]";
		static final String name = DbMap.Store.store_table+"."+"[store_name]";
		static final String street_address = DbMap.Store.store_table+"."+"[store_street_address]";
		static final String city = DbMap.Store.store_table+"."+"[store_city]";
		static final String state = DbMap.Store.store_table+"."+"[store_state]";
		static final String zip_code = DbMap.Store.store_table+"."+"[store_zip_code]";
		static final String phone_number = DbMap.Store.store_table+"."+"[store_phone_number]";
		static final String status_id = DbMap.Store.store_table+"."+"[store_status_id]";
	}	
	static class Store_product{
		static final String store_product_table = "[store_product]";
		static final String store_id = DbMap.Store_product.store_product_table+"."+"[store_id]";
		static final String product_upc = DbMap.Store_product.store_product_table+"."+"[product_upc]";
		static final String quantity = DbMap.Store_product.store_product_table+"."+"[store_product_quantity]";
		static final String price = DbMap.Store_product.store_product_table+"."+"[store_product_price]";
		static final String min_quantity = DbMap.Store_product.store_product_table+"."+"[min_product_quantity]";
		static final String status_id = DbMap.Store_product.store_product_table+"."+"[store_product_status_id]";
	}	
	static class Product{
		static final String product_table = "[product]";
		static final String upc = DbMap.Product.product_table+"."+"[product_upc]";
		static final String name = DbMap.Product.product_table+"."+"[product_name]";
		static final String description = DbMap.Product.product_table+"."+"[product_description]";

	}	
}
