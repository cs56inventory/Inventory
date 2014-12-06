import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class ServerWebSocketHandler extends Db {

	private Session session;
	private HashMap<String, Command> handlerMethods = new HashMap<String, Command>();

	/*
	 * Constructor Links the keys to handler methods
	 */
	public ServerWebSocketHandler() {
		super();
		handlerMethods.put("login", new CommandAdapter() {

			@Override
			public void runMethod(Object o) {
				login(o);
			}
		});
		
		handlerMethods.put("update_store_product", new CommandAdapter() {

			@Override
			public void runMethod(Object o) {
				updateStoreProduct(o);
			}
		});
		
		handlerMethods.put("update_order", new CommandAdapter() {

			@Override
			public void runMethod(Object o) {
				updateOrder(o);
			}
		});
	}

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
		System.out.println("Closing: code " + statusCode);
		ServerApp.userMap.remove(this.user.getUser_Id());
	}

	@OnWebSocketError
	public void onError(Throwable t) {
		System.out.println("Error: code " + t.getMessage());
	}

	@OnWebSocketConnect
	public void onConnect(Session s) {
		this.session = s;
		this.send("statuses", this.statuses);
		System.out.println("Connecting to:  "
				+ this.session.getRemoteAddress().getAddress());
	}

	private void refuseConnection() {
		System.out.println("refusing connection:  ");
		this.send("connection refused");
	}

	@OnWebSocketMessage
	public void onMessage(byte[] data, int offset, int length) {

		Object receivedObject = unwrapReceivedMessage(data);
		if (receivedObject instanceof ObjectWrapper) {
			ObjectWrapper wrappedObject = (ObjectWrapper) receivedObject;
			String _message = (String) (wrappedObject.getKey());
			Object unwrappedObject = wrappedObject.getObj();
			System.out.println("Server Received New Message:  " + _message);

			this.handlerMethods.getOrDefault(_message, new CommandAdapter() {
				@Override
				public void runMethod() {
					System.out.println("refusing connection:  ");
					refuseConnection();
				}
			}).runMethod(unwrappedObject);
		} else {
			refuseConnection();
		}
	}

	@OnWebSocketMessage
	public void onMessage(String msg) {

		System.out.println("Received Message:  " + msg);
		this.handlerMethods.getOrDefault(msg, new CommandAdapter() {
			@Override
			public void runMethod() {
				refuseConnection();
			}
		}).runMethod();
	}

	void send(String key, Object object) {

		ObjectWrapper data = new ObjectWrapper(key, object);
		try {
			System.out.println("Sending an object with key: " + key);
			this.session.getRemote().sendBytes(data.getBuffer());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void send(String message) {

		try {
			System.out.println("Sending the following message: " + message);
			this.session.getRemote().sendString(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Received data decoder
	 */
	public Object unwrapReceivedMessage(byte[] byts) {

		ObjectInputStream istream = null;
		Object obj = null;
		try {
			istream = new ObjectInputStream(new ByteArrayInputStream(byts));
			obj = istream.readObject();

			// if (obj instanceof ObjectWrapper) {
			// String _message = (String) ((ObjectWrapper) obj).getKey();
			// }
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}

	/*****************************************
	 * 
	 * BEGIN HANDLER METHODS
	 * 
	 *****************************************/
	
	private void login(Object receivedObject) {
		if (receivedObject instanceof User) {
			this.user = (User) receivedObject;
			this.getUserStoreMemberStore();

			if (this.user != null && this.user.getUser_Id() != 0) {

				ServerApp.userMap.put(this.user.getUser_Id(), this);

				this.send("login", this.user);
				// return user products, orders

				if (this.store_member != null && this.store_member.getStore_id() != 0
						&& this.store != null && this.store.getStore_id() != 0) {

					this.send("store", store);
					this.getStoreProducts();
					if (!this.products.isEmpty() && !this.storeProducts.isEmpty()) {

						this.send("products", this.products);
						this.send("store_products", this.storeProducts);
					}
				}
				this.getStoreOrders();
			} else {
				this.getUserDistributorMemberDistributor();
				if (this.user != null && this.user.getUser_Id() != 0) {

					ServerApp.userMap.put(this.user.getUser_Id(), this);

					this.send("login", this.user);
					// return user products, orders

					if (this.distributor_member != null
							&& this.distributor_member.getDistributor_id() != 0
							&& this.distributor != null
							&& this.distributor.getDistributor_id() != 0) {

						this.send("distributor", distributor);
						this.getDistributorProducts();
						if (!this.products.isEmpty() && !this.distributorProducts.isEmpty()) {

							this.send("products", this.products);
							this.send("distributor_products", this.distributorProducts);
						}
					}
					this.getDistributorOrders();
				}
			}

			if (!this.orders.isEmpty()) {
				if (!this.products.isEmpty()) {
					this.send("products", this.products);
				}
				this.send("orders", this.orders);
				this.send("order_products", this.orderProducts);
			}
		} else {
			this.send("login_failed");
		}
		;
	};

	public void updateStoreProduct(Object receivedObject) {
		if (receivedObject instanceof Store_Product) {
			Store_Product sp = (Store_Product) receivedObject;
			this.update(sp.getDbMappedValues(), new Db.StoreProductMap());
			this.storeProducts.put(sp.getProduct_upc(), sp);
			if (sp.getStore_product_quantity() < sp.getMin_product_quantity()) {
				Product_Distributor_Store pds = this.getStoreProductContract(sp
						.getProduct_upc());
				if (pds != null) {
					Float productTotalPrice = pds.getPds_product_unit_price()
							* pds.getMin_quantity() + pds.getPds_product_shipping_fee();
					Order newOrder = new Order(pds.getStore_id(),
							pds.getDistributor_id(), pds.getPds_product_shipping_fee(),
							productTotalPrice);

					int newOrderId = this.insert(newOrder.getDbMappedValues(),
							new Db.OrderMap());
					if (newOrderId > 0) {
						newOrder.setOrder_id(newOrderId);			
						Order_Product op = new Order_Product(newOrder.getOrder_id(),
								pds.getProduct_upc(), pds.getMin_quantity(), productTotalPrice);
						this.insert(op.getDbMappedValues(), new Db.OrderProductMap());
						
						this.getStoreOrders();
						
						if (!this.orders.isEmpty()) {
							if (!this.products.isEmpty()) {
								this.send("products", this.products);
							}
							this.send("orders", this.orders);
							this.send("order_products", this.orderProducts);
						}
						
						ArrayList<LinkedHashMap<String, String>> qryResults = this
								.getDistributorMembers(pds.getDistributor_id());
						if (!qryResults.isEmpty()) {
							try {
								for (int i = 0; i < qryResults.size(); i++) {
									HashMap<String, String> row = qryResults.get(i);
									Distributor_Member distributor_member = new Distributor_Member(
											row);
									ServerApp.sendToUser(distributor_member.getUser_id(),
											"order", newOrder);
								}
							} catch (Exception ex) {
								System.out.println(ex);
							}
						}
					}
				}
			}
		}
	}
	
	public void updateOrder(Object receivedObject){
		if(receivedObject instanceof Order){
			Order o = (Order)receivedObject;
			this.update(o.getDbMappedValues(), new Db.OrderMap());
			this.orders.put(o.getOrder_id(),o);
			ArrayList<LinkedHashMap<String,String>> qryResults = this.getStoreMembers(o.getStore_id());
			if( !qryResults.isEmpty() ){
				try{
					for( int i=0; i<qryResults.size(); i++){
						HashMap<String, String> row = qryResults.get(i);
						Store_Member store_member = new Store_Member(row);
						ServerApp.sendToUser(store_member.getUser_id(), "order", o);
					}
				}
				catch(Exception ex){
					System.out.println(ex);
				}
			}
		}
	}
	/*****************************************
	 * 
	 * END HANDLER METHODS
	 * 
	 *****************************************/
}
