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

	public ServerWebSocketHandler() {
		super();
		handlerMethods.put("login", new CommandAdapter() {

			@Override
			public void runMethod(Object o) {
				login(o);
			}
		});

		System.out.println("init: code ");
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
		System.out.println("Connecting to:  "
				+ this.session.getRemoteAddress().getAddress());

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

	@OnWebSocketMessage
	public void onMessage(byte[] data, int offset, int length) {

		Object receivedObject = unwrapReceivedMessage(data);
		if (receivedObject instanceof ObjectWrapper) {
			ObjectWrapper wrappedObject = (ObjectWrapper) receivedObject;
			String _message = (String) (wrappedObject.getKey());
			Object unwrappedObject = wrappedObject.getObj();
			System.out.println("Server Receved New Message:  " + _message);
	
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

	public Object unwrapReceivedMessage(byte[] byts) {

		ObjectInputStream istream = null;
		Object obj = null;
		try {
			istream = new ObjectInputStream(new ByteArrayInputStream(byts));
			obj = istream.readObject();

			if (obj instanceof ObjectWrapper) {
				String _message = (String) ((ObjectWrapper) obj).getKey();
				System.out.println("Server Receved New Message:  " + _message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}

	private void login(Object receivedObject) {
		if (receivedObject instanceof User) {
			this.user = (User) receivedObject;
			this.getUserStoreMemberStore();

			if (this.user != null && this.user.getUser_Id() != 0) {

				ServerApp.userMap.put(this.user.getUser_Id(), this.session);
				
				this.send("login", this.user);
				// return user products, orders

				if (this.store_member!=null && this.store_member.getStore_id() != 0 && this.store !=null && this.store.getStore_id()!=0) {
					
					this.send("store", store);
					this.getStoreProducts();
					if(!this.products.isEmpty() && !this.storeProducts.isEmpty()){
						
						this.send("products", this.products);
						this.send("store_products", this.storeProducts);
					}
				}
				this.getStoreOrders();
			} 
			else {

			}
			
			
			if(!this.orders.isEmpty()){
				if(!this.products.isEmpty()){
					this.send("products", this.products);
				}
				this.send("orders", this.orders);
			}
		} 
		else {
			this.send("login_failed");
		}
		;
	};

	private void refuseConnection() {
		System.out.println("refusing connection:  ");
		this.send("connection refused");
	}

	private void send(String key, Object object) {
		ObjectWrapper data = new ObjectWrapper(key, object);
		try {
			System.out.println("Sending an object with key: " + key);
			this.session.getRemote().sendBytes(data.getBuffer());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void send(String message) {
		try {
			System.out.println("Sending the following message: " + message);
			this.session.getRemote().sendString(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
