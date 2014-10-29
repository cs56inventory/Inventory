import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;


@WebSocket
public class ServerWebSocketHandler {
	
	private Session outbound;
	
	public ServerWebSocketHandler(){
		super();
		System.out.println("init: code ");
	}
	@OnWebSocketClose
	public void onClose( int statusCode, String reason){
		System.out.println("Closing: code "+statusCode);
	}
	
	@OnWebSocketError
	public void onError(Throwable t){
		System.out.println("Error: code "+t.getMessage());
	}
	
	@OnWebSocketConnect
	public void onConnect(Session session){
		this.outbound = session;
		System.out.println("Connecting to:  "+session.getRemoteAddress().getAddress());


		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
		  @Override
		  public void run() {
			  System.out.println("Hello Again! ");
			  try {
				outbound.getRemote().sendString("Hello client! ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		}, 5000, 5000);
	}
	
	@OnWebSocketMessage
	public void onMessage(byte[] data, int offset, int lenght){
//		ServerApp.userMap.put(key, value);
	}
	
	@OnWebSocketMessage
	public void onMessage(String message){
		System.out.println("Message:  "+message);
		
	}
}
