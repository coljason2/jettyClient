package com.jettyClient;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.api.Commands;
import com.model.Request.LoginRequest;
import com.model.keno.KenoBetItem;
import com.model.keno.KenoBetType;
import com.placebet.item;
import com.placebet.placeBetEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebSocket(maxTextMessageSize = 128 * 1024)
public class KenoClient extends AbstractSimpleEchoSocketClient {

	@OnWebSocketMessage
	public void onMessage(String msg) throws IOException, InterruptedException {
		Thread.sleep(1000);
		parseResponseMessage(msg);
		log.info("Got msg: {} ", msg);
		getCommandCode = Integer.parseInt(msg.substring(0, msg.indexOf(".")));
		switch (getCommandCode) {
		case Commands.Login:
			init_KenoStartGameRequest(msg);
			break;
		case Commands.QueryOdds:
			StartPlaceBet();
			break;
		}
	}

	@Override
	public void PlaceBet() {

	}

	@Override
	public void StartPlaceBet() {
		String rsp = response.getJsonArray("list").toString();
		rsp = rsp.substring(1, rsp.length() - 1);
		parseResponseMessage(rsp);
		System.out.println(response.getJsonArray("list").toString());
		kenoBetTypes = JSON.parseObject(response.getJsonArray("list").toString(),
				new TypeReference<List<KenoBetType>>() {
				});

		for (KenoBetType b : kenoBetTypes) {
			placebet = new placeBetEntity();
			placebet.setGameCode("KN");
			placebet.setSerialNo(loginResponse.getSerialNo());
			placebet.setSessionId(loginResponse.getSessionId());
			placebet.setToken(loginResponse.getToken());
			placebet.setBetType(b.getBetType());
			placebet.setMarket("KN01");
			// placebet.setDrawId();
			item item;
			int betAmount;
			for (KenoBetItem i : b.getItems()) {
				item = new item();
				item.getBetItem().add(i.getBetItem());
				item.setDrawType(b.getBetType());
				betAmount = (int) ((new Random().nextInt(10) + 10) * i.getOdds());
				placebet.getItems().add(item);
				placebet.setBetAmount(betAmount);
				placebet.setOdds(String.valueOf(i.getOdds()));
				sendMessage(Commands.PlaceBet, placebet);
				placebet.getItems().clear();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@OnWebSocketConnect
	public void onConnect(Session session) {
		this.session = session;
		try {
			sendMessage(1, new LoginRequest());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
