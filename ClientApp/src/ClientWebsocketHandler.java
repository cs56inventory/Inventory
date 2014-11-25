import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class ClientWebsocketHandler {
	private ClientApp app;
	private ClientInterface clientInterface;
	private final CountDownLatch closeLatch;
	private HashMap<String, Command> handlerMethods = new HashMap<String, Command>();
	User user = new User();
	Store store = new Store();
	LinkedHashMap<Integer, Store_Product> storeProducts = new LinkedHashMap<Integer, Store_Product>();
	LinkedHashMap<Integer, Product> products= new LinkedHashMap<Integer, Product>();
	LinkedHashMap<Integer, Order> orders = new LinkedHashMap<Integer, Order>();
	LinkedHashMap<Integer, LinkedHashMap<Integer, Order_Product>> orderProducts = new LinkedHashMap<Integer, LinkedHashMap<Integer, Order_Product>>();
	
	@SuppressWarnings("unused")
	/*
	 * Constructor
	 * Links the keys to handler methods in ClientInterface
	 * 
	 */
	public ClientWebsocketHandler(ClientApp app) {
		this.app = app;
		this.closeLatch = new CountDownLatch(1);
		this.clientInterface = app.getInterface();
		System.out.println("name "+clientInterface.g);
		//Repeatedly decrease randomly chosen store products' quantities here TODO
		handlerMethods.put("login", new CommandAdapter(){
		
			@Override
			public void runMethod(Object o) {
				if(o instanceof User){
					user = (User)o;
					System.out.println("userid "+user.getUser_Id());
					clientInterface.login();
				}
			}
		});	
		
		handlerMethods.put("login_failed", new CommandAdapter(){
			
			@Override
			public void runMethod() {
				clientInterface.loginFailed();
			}
		});	
		
		handlerMethods.put("products", new CommandAdapter(){

			@Override
			public void runMethod(Object o) {
				if(o instanceof LinkedHashMap<?, ?>){
					products.putAll((LinkedHashMap<Integer, Product>)o);
				}
			}
		});
		
		handlerMethods.put("store_products", new CommandAdapter(){

			@Override
			public void runMethod(Object o) {
				if(o instanceof LinkedHashMap<?, ?>){
					storeProducts = (LinkedHashMap<Integer, Store_Product>)o;

					System.out.println("store products "+storeProducts.values().toArray());
					clientInterface.fillStoreProducts();
					
					startProductQuantityUpdate();
				}
			}			
		});		
		//add fillOrders method in ClientInterface TODO
		handlerMethods.put("orders", new CommandAdapter(){

			@Override
			public void runMethod(Object o) {
				if(o instanceof LinkedHashMap<?, ?>){
					orders = (LinkedHashMap<Integer, Order>)o;

					System.out.println("store products "+orders.values().toArray());
//					clientInterface.fillOrders();
					//create fillOrders method in clientInterface to fill order table --TODO
				}
			}
		});
		
		handlerMethods.put("order_update", new CommandAdapter(){

			@Override
			public void runMethod(Object o) {
				if(o instanceof Order){
					Order updatedOrder = (Order)o;
					orders.put(updatedOrder.getOrder_id(), updatedOrder);
					System.out.println("Order update received: ");
//					clientInterface.updateOrder();
					//create updateOrder method in clientInterface to update order row --TODO
				}
			}
		});	
	}

	public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
		return this.closeLatch.await(duration, unit);
	}

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
		System.out.println("Connection closed: " + statusCode);
		ClientApp.session = null;
	}

	@OnWebSocketConnect
	public void onConnect(Session session) {
		System.out.println("Connected: %s%n" + session);
		ClientApp.session = session;
		clientInterface.enableBtnLogin();
	}
	
	@OnWebSocketMessage
	public void onMessage(byte[] data, int offset, int lenght){

		Object receivedObject = unwrapReceivedMessage(data);
    if(receivedObject instanceof ObjectWrapper){
    	ObjectWrapper wrappedObject = (ObjectWrapper)receivedObject;
    	String _message = wrappedObject.getKey();
    	Object unwrappedObject = wrappedObject.getObj();
    	System.out.println("New Message:  "+_message);
    	System.out.println("New object: " +wrappedObject.getObj() );
	  	this.handlerMethods.getOrDefault(_message, new CommandAdapter(){
		  	@Override public void runMethod(){
		  		refuseConnection();
		  	}
			}).runMethod(unwrappedObject);
    }
	  else{
	  	refuseConnection();
	  }
		System.out.println("Bytes:  "+data);
	}
	
	@OnWebSocketMessage
	public void onMessage(String msg) {
		
		System.out.printf("Got msg: %s%n", msg);
  	this.handlerMethods.getOrDefault(msg, new CommandAdapter(){
	  	@Override public void runMethod(){
	  		refuseConnection();
	  	}
		}).runMethod();
	}
	
	public Object unwrapReceivedMessage(byte[] byts) {
	     
        ObjectInputStream istream = null;
        Object obj = null;
        try {
            istream = new ObjectInputStream(new ByteArrayInputStream(byts));
            obj = istream.readObject();
     
            if(obj instanceof ObjectWrapper){
            	String _message = (String)((ObjectWrapper)obj).getKey();
            	System.out.println("New Message:  "+_message);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
		return obj;
  }
	
	private void refuseConnection() {
		this.send("connection refused");
	}
	
	void send(String key, Object object){

		ObjectWrapper	data = new ObjectWrapper(this.user.getUser_Id(), key, object);
		try {
			System.out.println(data.getBuffer());
			ClientApp.session.getRemote().sendBytes(data.getBuffer());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(NullPointerException e){
			this.app.createConnection();
		}
	}
	
	void send(String message){
		try {
			ClientApp.session.getRemote().sendString(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void startProductQuantityUpdate(){
		 Timer timer = new Timer();

		 timer.scheduleAtFixedRate(new TimerTask() {
			 @Override
			 public void run() {
				 System.out.println("Running product quantity updating timer! ");
				 decreaseRandomProductQuantity();
			 }
		 }, 5000, 5000);		
	}
	
	private void decreaseRandomProductQuantity(){
		Object[] setArray ;
		setArray = storeProducts.entrySet().toArray();
		int randNum = (new Random()).nextInt(setArray.length);
		Entry<Integer, Store_Product> randomStoreProductEntry= (Entry<Integer, Store_Product>)setArray[randNum];
		Store_Product randomProduct = randomStoreProductEntry.getValue();
		int oldQuantity = randomProduct.getStore_product_quantity();
		System.out.println("Store Product UPC "+randomProduct.getProduct_upc());
		System.out.println("Old Quantity "+oldQuantity);
		if(oldQuantity!=0){
			int newQuantity = oldQuantity-1;
			randomProduct.setStore_product_quantity(newQuantity);
			System.out.println("New Quantity "+newQuantity);
			clientInterface.updateTable(randomProduct);
			this.send("update_store_product", randomProduct);
		}
	}
}
