package com.GenIlottoGame.HL11x5;

import java.util.Random;

import com.GenIlottoGame.AbstractGeniLottoGame;
import com.model.Request.LoginResponse;
import com.placebet.item;
import com.placebet.placeBetEntity;

public class GetHLx5_G3BH extends AbstractGeniLottoGame {

	public GetHLx5_G3BH(LoginResponse loginRsp, int drawId) {
		// can change different market
		placebet.setMarket("GD");
		placebet.setGameCode("HL11x5");
		placebet.setBetType("G3BH");
		placebet.setSerialNo(loginRsp.getSerialNo());
		placebet.setSessionId(loginRsp.getSessionId());
		placebet.setToken(loginRsp.getToken());
		placebet.setDrawId(drawId);
		placebet.setMultiple(1);
		placebet.setBetCount(1);
		placebet.setBetUnit(2);
		placebet.setPrizeGroup("1950");
		placebet.setOdds("318.33");
		placebet.setComm(0.0303);
		placebet.setBetAmount(2);
	}

	@Override
	public placeBetEntity get_placeBet() {
		item = new item();
		item.setDrawType("G3H");
		while (RndNum.size() < 3) {
			int RNG = new Random().nextInt(10) + 1;
			if (RNG < 10) {
				item.getBetItem().add("0" + RNG);
			} else {
				item.getBetItem().add("" + RNG);
			}
			RndNum.add(RNG);
		}
		placebet.getItems().add(item);
		return placebet;
	}

}
