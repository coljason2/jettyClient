package com.GenIlottoGame.mark6;

import java.util.Map;
import java.util.Random;
import com.GenIlottoGame.AbstractGeniLottoGame;
import com.google.common.collect.ImmutableMap;
import com.model.Request.LoginResponse;
import com.placebet.item;
import com.placebet.placeBetEntity;

/*
 * 
 * 六合彩-不中
 * 
 * */
public class GetMark6_NO_B extends AbstractGeniLottoGame {

	private Map<Integer, String> BetTypes = new ImmutableMap.Builder<Integer, String>().put(5, "NO5B").put(6, "NO6B")
			.put(7, "NO7B").put(8, "NO8B").put(9, "NO9B").put(10, "NO10B").build();

	private Map<String, Double> Odds = new ImmutableMap.Builder<String, Double>().put("NO5B", 2.196799)
			.put("NO6B", 2.612409).put("NO7B", 3.120378).put("NO8B", 3.744453).put("NO9B", 4.51537)
			.put("NO10B", 5.473176).build();

	public GetMark6_NO_B(LoginResponse loginRsp, int drawId) {
		super();
		placebet.setSerialNo(loginRsp.getSerialNo());
		placebet.setSessionId(loginRsp.getSessionId());
		placebet.setToken(loginRsp.getToken());
		placebet.setDrawId(drawId);
		placebet.setMarket("HK");
		placebet.setGameCode("MARK6");
		placebet.setMultiple(1);
		placebet.setBetUnit(1);
		placebet.setBetCount(1);
		placebet.setComm(0.02);
		placebet.setPrizeGroup("1960");
		placebet.setBetAmount(new Random().nextInt(50) + 1);
	}

	public placeBetEntity get_placeBet() {
		int size = new Random().nextInt(5) + 5;
		String betType = BetTypes.get(size);
		item = new item();
		item.setDrawType(betType);
		createRandomNumber(size, 44);
		for (int i : RndNum) {
			String contain;
			if (i < 10)
				contain = "0" + i;
			else
				contain = i + "";
			item.getBetItem().add(contain);
		}

		placebet.setOdds(Odds.get(betType) + "");
		placebet.setBetType(betType);
		placebet.getItems().add(item);
		return placebet;
	}

	// public static void main(String[] args) {
	// GetMark6_NO_B test = new GetMark6_NO_B(new LoginResponse(), 45642);
	// System.out.println(test.get_placeBet());
	// }
}
