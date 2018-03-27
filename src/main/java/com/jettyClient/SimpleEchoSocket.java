package com.jettyClient;

import java.io.IOException;
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
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.GenIlottoGame.mark6.GetMark6_M6N7;
import com.GenIlottoGame.mark6.GetMark6_NO_B;
import com.GenIlottoGame.mark6.GetMark6_TAIL;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.api.Commands;
import com.api.CommandsLotto;
import com.model.LoginRequest;
import com.model.LoginResponse;
import com.model.StartGameRequest;
import com.model.gameMarket;
import com.model.RspDrawMarket;
import com.model.QueryDrawInfo;
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
	protected QueryDrawInfo QueryDrawInfo;
	protected List<RspDrawMarket> RspDrawMarket;
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
		//Thread.sleep(1000);
		parseResponseMessage(msg);
		log.info("Got msg: {} ", msg);
		// code = response.get("code").toString();
		// log.info("code : {}", new Object[] { code });
		getCommandCode = Integer.parseInt(msg.substring(0, msg.indexOf(".")));
		// log.info("getCommandCode : {}", getCommandCode);
		switch (getCommandCode) {
		case CommandsLotto.Login:
			initStartGameRequest(msg);
			break;
		case CommandsLotto.QueryDrawInfo:
			StartPlaceBet();
			break;
		case CommandsLotto.PlaceBet:
			PlaceBet();
			break;
		case Commands.DrawClosed:
			PlaceBet();
			break;
		}
	}

	private void PlaceBet() {
		GetMark6_M6N7 GetMark6_M6N7 = new GetMark6_M6N7(loginResponse.getSerialNo(), loginResponse.getSessionId(),
				loginResponse.getToken(), RspDrawMarket.get(0).getDrawId());
		GetMark6_NO_B GetMark6_NO_B = new GetMark6_NO_B(loginResponse.getSerialNo(), loginResponse.getSessionId(),
				loginResponse.getToken(), RspDrawMarket.get(0).getDrawId());
		sendMessage(CommandsLotto.PlaceBet, GetMark6_NO_B.get_placeBet());
	}

	private void StartPlaceBet() {
		RspDrawMarket = JSON.parseObject(response.getJsonArray("list").toString(),
				new TypeReference<List<RspDrawMarket>>() {
				});

		GetMark6_TAIL GetMark6_TAIL = new GetMark6_TAIL(loginResponse.getSerialNo(), loginResponse.getSessionId(),
				loginResponse.getToken(), RspDrawMarket.get(0).getDrawId());

		GetMark6_NO_B GetMark6_NO_B = new GetMark6_NO_B(loginResponse.getSerialNo(), loginResponse.getSessionId(),
				loginResponse.getToken(), RspDrawMarket.get(0).getDrawId());
		sendMessage(CommandsLotto.PlaceBet, GetMark6_NO_B.get_placeBet());

	}

	private void initStartGameRequest(String msg) {
		loginResponse = JSON.parseObject(msg.substring(msg.indexOf("{")), LoginResponse.class);
		QueryDrawInfo = new QueryDrawInfo();
		QueryDrawInfo.setSerialNo(loginResponse.getSerialNo());
		QueryDrawInfo.setSessionId(loginResponse.getSessionId());
		QueryDrawInfo.setToken(loginResponse.getToken());
		QueryDrawInfo.setMarket("HK");
		QueryDrawInfo.setGameCode("MARK6");
		sendMessage(CommandsLotto.QueryDrawInfo, QueryDrawInfo);
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
}
