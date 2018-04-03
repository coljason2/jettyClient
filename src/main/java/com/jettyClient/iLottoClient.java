package com.jettyClient;

import java.io.IOException;
import java.util.List;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.api.Commands;
import com.api.CommandsLotto;
import lombok.extern.slf4j.Slf4j;

import com.model.Request.LoginRequest;
import com.model.Request.RspDrawMarket;

@Slf4j
@WebSocket(maxTextMessageSize = 128 * 1024)
public class iLottoClient extends AbstractSimpleEchoSocketClient {

	@OnWebSocketMessage
	public void onMessage(String msg) throws IOException, InterruptedException {
		Thread.sleep(1000);
		parseResponseMessage(msg);
		log.info("Got msg: {} ", msg);
		// code = response.get("code").toString();
		// log.info("code : {}", new Object[] { code });
		getCommandCode = Integer.parseInt(msg.substring(0, msg.indexOf(".")));
		// log.info("getCommandCode : {}", getCommandCode);
		switch (getCommandCode) {
		case CommandsLotto.Login:
			init_iLottoStartGameRequest(msg);
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

	public void PlaceBet() {
		placebet = getilottoPlacebet();
		sendMessage(CommandsLotto.PlaceBet, placebet);
	}

	public void StartPlaceBet() {
		RspDrawMarket = JSON.parseObject(response.getJsonArray("list").toString(),
				new TypeReference<List<RspDrawMarket>>() {
				});
		placebet = getilottoPlacebet();
		sendMessage(CommandsLotto.PlaceBet, placebet);
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
}
