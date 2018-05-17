package com.GenIlottoGame.mark6;

import java.util.Random;

import com.GenIlottoGame.AbstractGeniLottoGame;
import com.model.Request.LoginResponse;
import com.placebet.item;
import com.placebet.placeBetEntity;

/*
 * 
 * 
 * 六合彩-生肖(特肖)
 * 
 * 
 * odds
 * 當年  9.604
 * 一般 12.005
 * */
public class GetMark6_ZN7 extends AbstractGeniLottoGame {

	public GetMark6_ZN7(LoginResponse loginRsp, int drawId) {
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
		placebet.setBetType("ZN7");
	}

	@Override
	public placeBetEntity get_placeBet() {
		item = new item();
		item.setDrawType("ZN7");
		int betCount = new Random().nextInt(11) + 1;
		int betamt = 0;
		createRandomNumber(betCount, 11);

		for (int i : RndNum) {
			item.getBetItem().add(i + "");
			int betamount = new Random().nextInt(50) + 1;
			item.getBetAmount().add(betamount + "");
			betamt = betamt + betamount;
		}
		placebet.getItems().add(item);
		placebet.setBetCount(betCount);
		placebet.setBetAmount(betamt);
		if (placebet.getItems().get(0).equals(ThisYearIndex)) {
			placebet.setOdds("9.604");
		} else {
			placebet.setOdds("12.005");
		}

		return placebet;
	}

	// public static void main(String[] args) {
	// GetMark6_ZN7 test = new GetMark6_ZN7("aaaaa", "aaaaa", "aaaaa", 45555);
	// System.out.println(test.get_placeBet());
	// }
}
