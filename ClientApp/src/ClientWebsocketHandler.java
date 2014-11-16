import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
					// Timer timer = new Timer();
					//
					// timer.scheduleAtFixedRate(new TimerTask() {
					// @Override
					// public void run() {
					// System.out.println("Hello Again! ");
					// send("Hello client! ");
					// }
					// }, 5000, 5000);
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
				storeProducts = (LinkedHashMap<Integer, Store_Product>)o;

				System.out.println("store products "+storeProducts.values().toArray());
				clientInterface.fillStoreProducts();
			}
		});		
		//add fillOrders method in ClientInterface TODO
		handlerMethods.put("orders", new CommandAdapter(){

			@Override
			public void runMethod(Object o) {
				orders = (LinkedHashMap<Integer, Order>)o;

				System.out.println("store products "+orders.values().toArray());
//				clientInterface.fillOrders();
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
		System.out.println("Got connect: %s%n" + session);
		ClientApp.session = session;
	}
	
	@OnWebSocketMessage
	public void onMessage(byte[] data, int offset, int lenght){

		Object receivedObject = unwrapReceivedMessage(data);
    if(receivedObject instanceof ObjectWrapper){
    	ObjectWrapper wrappedObject = (ObjectWrapper)receivedObject;
    	String _message = (String)(wrappedObject.getKey());
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
		ObjectWrapper data = new ObjectWrapper(key, object);
		try {
			System.out.println(data.getBuffer());
			ClientApp.session.getRemote().sendBytes(data.getBuffer());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(NullPointerException e){
			
			System.out.println("failed "+this.app.getClient().isFailed());
			System.out.println("isrunning  "+this.app.getClient().isRunning());
			System.out.println("stopped  "+this.app.getClient().isStopped());
			System.out.println("started  "+this.app.getClient().isStarted());
			System.out.println("isstarting  "+this.app.getClient().isStarting());
			this.app.createConnection();
			System.out.println("isstarting  "+this.app.getClient().isStarting());
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
}
