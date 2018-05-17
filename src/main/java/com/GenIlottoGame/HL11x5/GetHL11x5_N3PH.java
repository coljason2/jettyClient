package com.GenIlottoGame.HL11x5;

import java.util.Random;

import com.GenIlottoGame.AbstractGeniLottoGame;
import com.model.Request.LoginResponse;
import com.placebet.item;
import com.placebet.placeBetEntity;

//前三11選五
public class GetHL11x5_N3PH extends AbstractGeniLottoGame {

	public GetHL11x5_N3PH(LoginResponse loginRsp, int drawId) {
		// can change different market
		placebet.setMarket("GD");
		placebet.setSerialNo(loginRsp.getSerialNo());
		placebet.setSessionId(loginRsp.getSessionId());
		placebet.setToken(loginRsp.getToken());
		placebet.setDrawId(drawId);
		placebet.setGameCode("HL11x5");
		placebet.setMultiple(1);
		placebet.setBetCount(1);
		placebet.setBetUnit(2);
		placebet.setPrizeGroup("1950");
		placebet.setBetType("N3PH");
		placebet.setOdds("1930");
		placebet.setComm(0.0202);
		placebet.setBetAmount(2);
	}

	@Override
	public placeBetEntity get_placeBet() {
		for (int i = 1; i <= 3; i++) {
			item = new item();
			item.setDrawType(i + "");
			int RNG = new Random().nextInt(10) + 1;
			if (RNG < 10) {
				item.getBetItem().add("0" + RNG);
			} else {
				item.getBetItem().add("" + RNG);
			}
			placebet.getItems().add(item);
		}

		return placebet;
	}

}
