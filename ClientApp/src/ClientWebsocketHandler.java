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
	  @SuppressWarnings("unused")
	    private Session session;
	  
	public ClientWebsocketHandler(){
		this.closeLatch = new CountDownLatch(1);
	
	}
	
	public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException{
		return this.closeLatch.await(duration,  unit);
	}
	
	@OnWebSocketClose
	public void onClose(int statusCode, String reason){
		System.out.println("Connection closed: "+statusCode);
		this.session = null;
	}
	
	@OnWebSocketConnect
	public void onConnect(Session session){
		System.out.println("Got connect: %s%n"+session);
		this.session = session;
		try{
//			User u;
//			ObjectWrapper data = new ObjectWrapper(new String("connect"), User);
//			session.getRemote().sendBytes(data);
			Future<Void> fut;
//			fut = session.getRemote().sendBytesByFuture(data)
			fut = session.getRemote().sendStringByFuture("Gevorg");
			fut.get(2, TimeUnit.SECONDS);
			fut = session.getRemote().sendStringByFuture("thanks");
			fut.get(2, TimeUnit.SECONDS);
//			session.close(StatusCode.NORMAL, "done");
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
	
    @OnWebSocketMessage
    public void onMessage(String msg) {
        System.out.printf("Got msg: %s%n", msg);
    }
}
