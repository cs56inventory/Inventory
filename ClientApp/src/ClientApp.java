import java.net.URI;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

public class ClientApp {
	public static Session session;
	private WebSocketClient client;
	private ClientWebsocketHandler socket;
	private ClientInterface clientInterface;
	
	public static void main(String[] args) {
		new ClientApp();
	}
	public ClientApp() {
		// TODO Auto-generated method stub
		this.clientInterface = new ClientInterface(this);
		createConnection();
	}
	public void createConnection(){
		
		if(socket == null){
			socket = new ClientWebsocketHandler(this);
		}
		try {
			if(client==null || !client.isRunning()){
				client = new WebSocketClient();
				client.setMaxIdleTimeout(86400000);
				client.start();


			}

			URI serverUri = new URI("ws://localhost:8080/");
			ClientUpgradeRequest request = new ClientUpgradeRequest();
			client.connect(socket, serverUri, request);
			System.out.printf("connecting to: %s%n", serverUri);

			
			// socket.awaitClose(15, TimeUnit.SECONDS);
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				// client.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public WebSocketClient getClient() {
		return client;
	}
	public ClientWebsocketHandler getSocket() {
		return socket;
	}
	public ClientInterface getInterface() {
		return clientInterface;
	}
}
