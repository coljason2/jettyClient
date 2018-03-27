package com.GenIlottoGame.mark6;

import java.util.Map;
import java.util.Random;

import com.GenIlottoGame.AbstractGeniLottoGame;
import com.google.common.collect.ImmutableMap;
import com.placebet.item;
import com.placebet.placeBetEntity;

public class GetMark6_HALF extends AbstractGeniLottoGame {

	private Map<Integer, String> BetTypes = new ImmutableMap.Builder<Integer, String>().put(0, "RB").put(1, "RS")
			.put(2, "RO").put(3, "RE").put(4, "RGO").put(5, "RGE").put(6, "GB").put(7, "GS").put(8, "GO").put(9, "GE")
			.put(10, "GGO").put(11, "GGE").put(12, "BB").put(13, "BS").put(14, "BO").put(15, "BE").put(16, "BGO")
			.put(17, "BGE").build();

	private Map<String, Double> Odds = new ImmutableMap.Builder<String, Double>().put("RB", 6.86).put("RS", 4.802)
			.put("RO", 6.0025).put("RE", 5.335556).put("RGO", 5.335556).put("RGE", 6.0025).put("GB", 5.335556)
			.put("GS", 6.86).put("GO", 6.0025).put("GE", 6.0025).put("GGO", 6.0025).put("GGE", 6.0025).put("BB", 6.0025)
			.put("BS", 6.86).put("BO", 6.0025).put("BE", 6.86).put("BGO", 6.86).put("BGE", 6.0025).build();

	public GetMark6_HALF(String serialNo, String sessionId, String token, int drawId) {
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
		placebet.setBetType("HALF");
	}

	@Override
	public placeBetEntity get_placeBet() {
		item = new item();
		item.setDrawType("HALF");
		int betcount = new Random().nextInt(17) + 1;
		int betamt = 0;

		while (item.getBetItem().size() < betcount) {
			String betItem = BetTypes.get(new Random().nextInt(17) + 1);
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

//	public static void main(String[] args) {
//		GetMark6_HALF GetMark6_HALF = new GetMark6_HALF("ggggggg", "ggggggggg", "ggggggg", 5566);
//		System.out.println(GetMark6_HALF.get_placeBet());
//
//	}
}
