package com.jettyClient;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.api.Commands;
import com.google.common.collect.ImmutableMap;
import com.model.QueryDrawInfo;
import com.model.Request.LoginRequest;
import com.model.keno.KenoBetItem;
import com.model.keno.KenoBetType;
import com.placebet.item;
import com.placebet.placeBetEntity;
import com.model.keno.DrawResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebSocket(maxTextMessageSize = 128 * 1024)
public class KenoClient extends AbstractSimpleEchoSocketClient {

	private int NowDrawNumber;
	private Map<Integer, String> KenoMarket = new ImmutableMap.Builder<Integer, String>().put(1, "BJ").put(2, "WCA")
			.put(3, "KN01").put(4, "KN02").put(5, "KN03").put(6, "KN04").build();
	private String NowMarket;

	@OnWebSocketMessage
	public void onMessage(String msg) throws IOException, InterruptedException {
		Thread.sleep(500);
		parseResponseMessage(msg);
		log.info("Got msg: {} ", msg);
		getCommandCode = Integer.parseInt(msg.substring(0, msg.indexOf(".")));
		switch (getCommandCode) {
		case Commands.Login:
			init_KenoStartGameRequest(msg);
			break;
		case Commands.QueryOdds:
			GetAllBetType();
			break;
		case Commands.QueryDrawInfo:
			StartPlaceBet();
			break;
		// case Commands.PlaceBet:
		// GetMarketDrawNumber();
		// break;
		}
	}

	private void GetAllBetType() {
		String rsp = response.getJsonArray("list").toString();
		rsp = rsp.substring(1, rsp.length() - 1);
		parseResponseMessage(rsp);
		kenoBetTypes = JSON.parseObject(response.getJsonArray("list").toString(),
				new TypeReference<List<KenoBetType>>() {
				});
		GetMarketDrawNumber();
	}

	@Override
	public void PlaceBet() {
		String parsString = response.getJsonArray("list").toString().substring(1,
				response.getJsonArray("list").toString().length() - 1);
		JsonReader reader = Json.createReader(new StringReader(parsString));
		JsonObject rsp = reader.readObject();
		reader.close();
		NowDrawNumber = Integer.parseInt(rsp.get("drawId").toString());
		log.info("NowMarket = {} , NowDrawNumber = {}", new Object[] { NowMarket, NowDrawNumber });
		sendPlaceBet(NowDrawNumber, NowMarket);

	}

	private void sendPlaceBet(int DrawNumber, String market) {

		for (KenoBetType b : kenoBetTypes) {
			placebet = new placeBetEntity();
			placebet.setGameCode("KN");
			placebet.setSerialNo(loginResponse.getSerialNo());
			placebet.setSessionId(loginResponse.getSessionId());
			placebet.setToken(loginResponse.getToken());
			placebet.setBetType(b.getBetType());
			placebet.setMarket(market);
			placebet.setDrawId(DrawNumber);
			item item;
			int betAmount;
			for (KenoBetItem i : b.getItems()) {
				item = new item();
				item.setDrawType(b.getBetType());
				betAmount = (int) ((new Random().nextInt(10) + 10) * i.getOdds());
				if ("Ball".equals(b.getBetType())) {
					item.getBetItem().add((String.valueOf(new Random().nextInt(79) + 1)));
				} else {
					item.getBetItem().add(i.getBetItem());
				}

				placebet.getItems().add(item);
				placebet.setBetAmount(betAmount);
				placebet.setOdds(String.valueOf(i.getOdds()));
				sendMessage(Commands.PlaceBet, placebet);
				placebet.getItems().clear();

				if ("Ball".equals(b.getBetType()))
					break;
			}
		}
		GetMarketDrawNumber();
	}

	public void GetMarketDrawNumber() {
		NowMarket = KenoMarket.get(new Random().nextInt(5) + 1);
		QueryDrawInfo = new QueryDrawInfo();
		QueryDrawInfo.setSerialNo(loginResponse.getSerialNo());
		QueryDrawInfo.setSessionId(loginResponse.getSessionId());
		QueryDrawInfo.setToken(loginResponse.getToken());
		QueryDrawInfo.setGameCode("KN");
		QueryDrawInfo.setMarket(NowMarket);
		sendMessage(Commands.QueryDrawInfo, QueryDrawInfo);

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

	@Override
	public void StartPlaceBet() {
		PlaceBet();
	}

}
