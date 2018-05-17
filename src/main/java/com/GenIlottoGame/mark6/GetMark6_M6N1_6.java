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
 * 六合彩-正特
 * 
 * */
public class GetMark6_M6N1_6 extends AbstractGeniLottoGame {

	private Map<Integer, String> BetTypes = new ImmutableMap.Builder<Integer, String>().put(0, "M6N1").put(1, "M6N2")
			.put(2, "M6N3").put(3, "M6N4").put(4, "M6N5").put(5, "M6N6").build();

	public GetMark6_M6N1_6(LoginResponse loginRsp, int drawId) {
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
		placebet.setPrizeGroup("1960");
		placebet.setOdds("48.02");
	}

	public placeBetEntity get_placeBet() {
		String betType = BetTypes.get(new Random().nextInt(5));
		item = new item();
		item.setDrawType(betType);
		int betcount = new Random().nextInt(48) + 1;
		int betamt = 0;
		createRandomNumber(betcount, 48);

		for (int i : RndNum) {
			String contain;
			if (i < 10)
				contain = "0" + i;
			else
				contain = i + "";
			item.getBetItem().add(contain);
			int betamount = new Random().nextInt(50) + 1;
			item.getBetAmount().add(betamount + "");
			betamt = betamt + betamount;

		}
		placebet.setBetType(betType);
		placebet.setBetAmount(betamt);
		placebet.setBetCount(betcount);
		placebet.getItems().add(item);
		return placebet;
	}

}
