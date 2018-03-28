package com.GenIlottoGame.mark6;

import java.util.Random;
import com.GenIlottoGame.AbstractGeniLottoGame;
import com.model.Request.LoginResponse;
import com.placebet.item;
import com.placebet.placeBetEntity;

/*
 * 
 * 六合彩-特碼
 * 
 * */
public class GetMark6_M6N7 extends AbstractGeniLottoGame {

	public GetMark6_M6N7(LoginResponse loginRsp, int drawId) {
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
		placebet.setBetType("M6N7");
		placebet.setOdds("48.02");
	}

	public placeBetEntity get_placeBet() {
		item = new item();
		item.setDrawType("M6N7");
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
		placebet.setBetAmount(betamt);
		placebet.setBetCount(betcount);
		placebet.getItems().add(item);
		return placebet;
	}
}
