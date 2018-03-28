package com.GenIlottoGame.mark6;

import java.util.Map;
import java.util.Random;

import com.GenIlottoGame.AbstractGeniLottoGame;
import com.google.common.collect.ImmutableMap;
import com.placebet.item;
import com.placebet.placeBetEntity;

/*
 * 
 * 六合彩-總分
 * 
 * 
 * */

public class GetMark6_M6T extends AbstractGeniLottoGame {
	private Map<Integer, String> BetTypes = new ImmutableMap.Builder<Integer, String>().put(1, "big").put(2, "odd")
			.put(3, "bigodd").put(4, "smallodd").put(5, "small").put(6, "even").put(7, "bigeven").put(8, "smalleven")
			.build();

	private Map<String, Double> Odds = new ImmutableMap.Builder<String, Double>().put("big", 1.96).put("odd", 1.96)
			.put("bigodd", 3.92).put("smallodd", 3.92).put("small", 1.96).put("even", 1.96).put("bigeven", 3.92)
			.put("smalleven", 3.92).build();

	public GetMark6_M6T(String serialNo, String sessionId, String token, int drawId) {
		super();
		placebet.setSerialNo(serialNo);
		placebet.setSessionId(sessionId);
		placebet.setToken(token);
		placebet.setDrawId(drawId);
		placebet.setMarket("HK");
		placebet.setGameCode("MARK6");
		placebet.setMultiple(1);
		placebet.setBetUnit(1);
		placebet.setComm(0.02);
		placebet.setPrizeGroup("1950");
		placebet.setBetType("M6T");
	}

	@Override
	public placeBetEntity get_placeBet() {

		item = new item();
		item.setDrawType("M6T");
		int betcount = new Random().nextInt(7) + 1;
		int betamt = 0;
		while (item.getBetItem().size() < betcount) {
			String betItem = BetTypes.get(new Random().nextInt(7) + 1);
			if (!item.getBetItem().contains(betItem)) {
				item.getBetItem().add(betItem);
				int betAmount = new Random().nextInt(50) + 1;
				item.getBetAmount().add(betAmount + "");
				betamt = betamt + betAmount;
			}
		}

		placebet.setOdds(Odds.get(item.getBetItem().get(0)) + "");
		placebet.setBetAmount(betamt);
		placebet.setBetCount(betcount);
		placebet.getItems().add(item);

		return placebet;
	}

	// public static void main(String[] args) {
	// GetMark6_M6T GetMark6_M6T = new GetMark6_M6T("ggggggg", "ggggggggg",
	// "ggggggg", 5566);
	// System.out.println(GetMark6_M6T.get_placeBet());
	//
	// }
}
