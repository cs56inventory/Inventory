import java.net.URI;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

public class ClientApp {
	public static Session session;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebSocketClient client = new WebSocketClient();
		ClientWebsocketHandler socket = new ClientWebsocketHandler();
		try {
			client.start();
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

}
