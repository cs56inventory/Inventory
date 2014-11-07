import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class ClientWebsocketHandler {
	private final CountDownLatch closeLatch;
	private HashMap<String, Command> handlerMethods = new HashMap<String, Command>();
	@SuppressWarnings("unused")
	

	public ClientWebsocketHandler() {
		this.closeLatch = new CountDownLatch(1);
		handlerMethods.put("products", new Command(){

			@Override
			public void runMethod(Object o) {
				this.setProducts(o);
			}

			@Override
			public void runMethod() {
			}
		});
		handlerMethods.put("store_products", new Command(){

			@Override
			public void runMethod(Object o) {
				this.setStoreProducts(o);
			}

			@Override
			public void runMethod() {
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
		new ClientInterface();

	}
	
	public void onMessage(byte[] data, int offset, int lenght){

		Object receivedObject = unwrapReceivedMessage(data);
    if(receivedObject instanceof ObjectWrapper){
    	ObjectWrapper wrappedObject = (ObjectWrapper)receivedObject;
    	String _message = (String)(wrappedObject.getKey());
    	Object unwrappedObject = wrappedObject.getObj();
    	System.out.println("New Message:  "+_message);
    	
	  	this.handlerMethods.getOrDefault(_message, new Command(){
		  	@Override public void runMethod(){
		  		refuseConnection();
		  	}
				@Override
				public void runMethod(Object o) {
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
	
	private void send(String key, Object object){
		ObjectWrapper data = new ObjectWrapper(key, object);
		try {
			ClientApp.session.getRemote().sendBytes(data.getBuffer());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void send(String message){
		try {
			ClientApp.session.getRemote().sendString(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
