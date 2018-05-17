package com.jettyClient;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

/**
 * 2018-03-26 ilotto websocket client
 *
 */
public class App {
	public static void main(String[] args) {
		String destUri = "ws://ws-keno-dev.sgplay.biz/";
		WebSocketClient client = new WebSocketClient();
		 //iLottoClient socket = new iLottoClient();
		KenoClient socket = new KenoClient();
		//iLottoClient socket = new iLottoClient();
		try {
			client.start();
			URI echoUri = new URI(destUri);
			ClientUpgradeRequest request = new ClientUpgradeRequest();
			client.connect(socket, echoUri, request);
			socket.awaitClose(5, TimeUnit.SECONDS);
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
