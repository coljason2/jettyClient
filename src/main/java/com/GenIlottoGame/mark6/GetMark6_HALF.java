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
 * 六合彩-半波
 * 
 * */
public class GetMark6_HALF extends AbstractGeniLottoGame {

	private Map<Integer, String> BetTypes = new ImmutableMap.Builder<Integer, String>().put(1, "RB").put(2, "RS")
			.put(3, "RO").put(4, "RE").put(5, "RGO").put(6, "RGE").put(7, "GB").put(8, "GS").put(9, "GO").put(10, "GE")
			.put(11, "GGO").put(12, "GGE").put(13, "BB").put(14, "BS").put(15, "BO").put(16, "BE").put(17, "BGO")
			.put(18, "BGE").build();

	private Map<String, Double> Odds = new ImmutableMap.Builder<String, Double>().put("RB", 6.86).put("RS", 4.802)
			.put("RO", 6.0025).put("RE", 5.335556).put("RGO", 5.335556).put("RGE", 6.0025).put("GB", 5.335556)
			.put("GS", 6.86).put("GO", 6.0025).put("GE", 6.0025).put("GGO", 6.0025).put("GGE", 6.0025).put("BB", 6.0025)
			.put("BS", 6.86).put("BO", 6.0025).put("BE", 6.86).put("BGO", 6.86).put("BGE", 6.0025).build();

	public GetMark6_HALF(LoginResponse loginRsp, int drawId) {
		super();
		placebet.setSerialNo(loginRsp.getSerialNo());
		placebet.setSessionId(loginRsp.getSessionId());
		placebet.setToken(loginRsp.getToken());
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
		createRandomNumber(betcount, 17);
		for (int i : RndNum) {
			item.getBetItem().add(BetTypes.get(i));
			int betAmount = new Random().nextInt(50) + 1;
			item.getBetAmount().add(betAmount + "");
			betamt = betamt + betAmount;
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
//	}
}
