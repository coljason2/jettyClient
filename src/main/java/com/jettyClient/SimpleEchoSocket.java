package com.jettyClient;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.api.CommandsLotto;
import com.model.LoginRequest;
import com.model.LoginResponse;
import com.model.StartGameRequest;
import com.model.gameMarket;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebSocket(maxTextMessageSize = 128 * 1024)
public class SimpleEchoSocket {
	protected JsonObject response = null;
	public String code;
	public int getCommandCode;
	protected LoginResponse loginResponse = new LoginResponse();
	protected StartGameRequest startGameRequest;
	protected List<gameMarket> gameMarketList;
	private final CountDownLatch closeLatch;

	@SuppressWarnings("unused")
	private Session session;

	public SimpleEchoSocket() {
		this.closeLatch = new CountDownLatch(1);
	}

	public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
		return this.closeLatch.await(duration, unit);
	}

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
		System.out.printf("Connection closed: %d - %s%n", statusCode, reason);
		this.session = null;
		this.closeLatch.countDown();
	}

	@OnWebSocketConnect
	public void onConnect(Session session) {

		this.session = session;
		try {
			sendMessage(101, new LoginRequest());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@OnWebSocketMessage
	public void onMessage(String msg) throws IOException, InterruptedException {
		Thread.sleep(650);
		parseResponseMessage(msg);
		log.info("Got msg: {} ", msg);
		code = response.get("code").toString();
		log.info("code : {}", new Object[] { code });
		getCommandCode = Integer.parseInt(msg.substring(0, msg.indexOf(".")));
		log.info("getCommandCode : {}", getCommandCode);
		switch (getCommandCode) {
		case CommandsLotto.Login:
			initStartGameRequest(msg);
			break;
		case CommandsLotto.QueryGameMarketList:
			initGameSettingAll(msg);
			break;
		}
	}

	private void initGameSettingAll(String msg) {
		gameMarketList = JSON.parseObject(response.getJsonArray("list").toString(),
				new TypeReference<List<gameMarket>>() {
				});
		sendMessage(CommandsLotto.QueryGameSettingAll, startGameRequest);
	}

	private void initStartGameRequest(String msg) {
		loginResponse = JSON.parseObject(msg.substring(msg.indexOf("{")), LoginResponse.class);
		startGameRequest = new StartGameRequest();
		startGameRequest.setSerialNo(loginResponse.getSerialNo());
		startGameRequest.setSessionId(loginResponse.getSessionId());
		startGameRequest.setToken(loginResponse.getToken());
		sendMessage(CommandsLotto.QueryGameMarketList, startGameRequest);

	}

	public void sendMessage(int commandCode, Object message) {
		// log.info("sendMessage : {}", commandCode + "." +
		// JSON.toJSONString(message));
		this.session.getRemote().sendStringByFuture(commandCode + "." + JSON.toJSONString(message));
	}

	public void parseResponseMessage(String message) {
		// System.out.println(message);
		JsonReader reader = Json.createReader(new StringReader(message.substring(message.indexOf("{"))));
		response = reader.readObject();
		reader.close();
	}
}
