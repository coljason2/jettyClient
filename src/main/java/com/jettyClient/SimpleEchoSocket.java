package com.jettyClient;

import java.io.IOException;
import java.util.List;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.api.Commands;
import com.api.CommandsLotto;
import lombok.extern.slf4j.Slf4j;
import com.model.Request.RspDrawMarket;

@Slf4j
@WebSocket(maxTextMessageSize = 128 * 1024)
public class SimpleEchoSocket extends AbstractSimpleEchoSocketClient {

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

	public void PlaceBet() {
		placebet = getPlacebet();
		sendMessage(CommandsLotto.PlaceBet, placebet);
	}

	public void StartPlaceBet() {
		RspDrawMarket = JSON.parseObject(response.getJsonArray("list").toString(),
				new TypeReference<List<RspDrawMarket>>() {
				});
		placebet = getPlacebet();
		sendMessage(CommandsLotto.PlaceBet, placebet);
	}

}
