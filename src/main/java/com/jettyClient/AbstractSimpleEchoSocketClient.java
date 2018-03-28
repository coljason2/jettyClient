package com.jettyClient;

import java.io.StringReader;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.GenIlottoGame.mark6.Mark6AllGames;
import com.alibaba.fastjson.JSON;
import com.api.CommandsLotto;
import com.model.QueryDrawInfo;
import com.model.gameMarket;
import com.model.Request.LoginRequest;
import com.model.Request.LoginResponse;
import com.model.Request.RspDrawMarket;
import com.model.Request.StartGameRequest;
import com.placebet.placeBetEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebSocket(maxTextMessageSize = 128 * 1024)
abstract public class AbstractSimpleEchoSocketClient {
	public JsonObject response = null;
	public String code;
	public int getCommandCode;
	public LoginResponse loginResponse = new LoginResponse();
	public StartGameRequest startGameRequest;
	public List<gameMarket> gameMarketList;
	public QueryDrawInfo QueryDrawInfo;
	public List<RspDrawMarket> RspDrawMarket;
	public final CountDownLatch closeLatch;
	public placeBetEntity placebet;
	private Session session;

	public AbstractSimpleEchoSocketClient() {
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

	public void sendMessage(int commandCode, Object message) {
		log.info("sendMessage : {}", commandCode + "." + JSON.toJSONString(message));
		Future<Void> fut;
		fut = this.session.getRemote().sendStringByFuture(commandCode + "." + JSON.toJSONString(message));
		try {
			fut.get(10, TimeUnit.SECONDS); // wait message send 5 seconds
		} catch (InterruptedException e) {
			session.close(StatusCode.SERVER_ERROR, "InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			session.close(StatusCode.SERVER_ERROR, "ExecutionException");
			e.printStackTrace();
		} catch (TimeoutException e) {
			session.close(StatusCode.SERVER_ERROR, "TimeoutException");
			e.printStackTrace();
		}
	}

	public void parseResponseMessage(String message) {
		// System.out.println(message);
		JsonReader reader = Json.createReader(new StringReader(message.substring(message.indexOf("{"))));
		response = reader.readObject();
		reader.close();
	}

	public void initStartGameRequest(String msg) {
		loginResponse = JSON.parseObject(msg.substring(msg.indexOf("{")), LoginResponse.class);
		QueryDrawInfo = new QueryDrawInfo();
		QueryDrawInfo.setSerialNo(loginResponse.getSerialNo());
		QueryDrawInfo.setSessionId(loginResponse.getSessionId());
		QueryDrawInfo.setToken(loginResponse.getToken());
		QueryDrawInfo.setMarket("HK");
		QueryDrawInfo.setGameCode("MARK6");
		sendMessage(CommandsLotto.QueryDrawInfo, QueryDrawInfo);
	}

	abstract public void PlaceBet();

	abstract public void StartPlaceBet();
}
