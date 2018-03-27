package com.GenIlottoGame.mark6;

import java.util.Random;

import com.GenIlottoGame.AbstractGeniLottoGame;
import com.placebet.item;
import com.placebet.placeBetEntity;

public class GetMark6_TAIL extends AbstractGeniLottoGame {

	// placebet.setBetCount(1);
	// placebet.setBetAmount(new Random().nextInt(200) + 1);
	public GetMark6_TAIL(String serialNo, String sessionId, String token, int drawId) {
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
		placebet.setBetType("TAIL");

	}

	public placeBetEntity get_placeBet() {
		item = new item();
		item.setDrawType("TAIL");
		int betcount = new Random().nextInt(8) + 1;
		int betamt = 0;
		while (item.getBetItem().size() != betcount) {
			int Random = new Random().nextInt(9);
			if (!item.getBetItem().contains(Random + "")) {
				item.getBetItem().add(Random + "");
				int betamount = new Random().nextInt(50) + 1;
				item.getBetAmount().add(betamount + "");
				betamt = betamt + betamount;
			}
		}
		if (item.getBetItem().get(0).equals("0")) {
			placebet.setOdds("2.077507");
		} else {
			placebet.setOdds("1.769284");
		}
		placebet.setBetAmount(betamt);
		placebet.setBetCount(betcount);
		placebet.getItems().add(item);
		return placebet;
	}

	// public static void main(String[] args) {
	// GetMark6_TAIL test = new GetMark6_TAIL("aaaaa", "aaaaa", "aaaaa", 45555);
	// System.out.println(test.get_placeBet());
	// }
}
