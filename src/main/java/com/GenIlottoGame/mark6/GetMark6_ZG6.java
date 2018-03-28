package com.GenIlottoGame.mark6;

import java.util.Random;
import com.GenIlottoGame.AbstractGeniLottoGame;
import com.model.Request.LoginResponse;
import com.placebet.item;
import com.placebet.placeBetEntity;

/*
 * 
 * 六合彩-生肖(六肖)
 * 6->1
 * 7->7
 * 8->28
 * 9->84
 * 10->210
 * 11->462
 * 12->924
 * 
 * 一般
 * odds = 2.000833 
 * 當年
 * odds = 1.9208
 * */
public class GetMark6_ZG6 extends AbstractGeniLottoGame {

	public GetMark6_ZG6(LoginResponse loginRsp, int drawId) {
		super();
		placebet.setSerialNo(loginRsp.getSerialNo());
		placebet.setSessionId(loginRsp.getSessionId());
		placebet.setToken(loginRsp.getToken());
		placebet.setDrawId(drawId);
		placebet.setMarket("HK");
		placebet.setGameCode("MARK6");
		placebet.setMultiple(1);
		placebet.setComm(0.02);
		placebet.setPrizeGroup("1950");
		placebet.setBetType("ZG6");
	}

	public int countBetcount(int i) {
		if (i == 6) {
			return 1;
		} else {
			int m = 1;
			int s = 1;
			int j = i - 6;
			for (int k = j; k > 0; k--) {
				m = m * i;
				s = s * j;
				i--;
				j--;
			}
			return m / s;
		}

	}

	@Override
	public placeBetEntity get_placeBet() {

		item = new item();
		item.setDrawType("ZG6");
		int size = new Random().nextInt(5) + 6;
		int betuint = new Random().nextInt(100) + 1;
		while (RndNum.size() < size) {
			int Random = new Random().nextInt(11) + 1;
			RndNum.add(Random);
		}
		for (int i : RndNum) {
			item.getBetItem().add(i + "");
		}
		placebet.setBetUnit(betuint);
		placebet.setBetAmount(betuint * countBetcount(size));
		placebet.setBetCount(countBetcount(size));
		placebet.getItems().add(item);

		if (placebet.getItems().get(0).equals(ThisYearIndex)) {
			placebet.setOdds("1.9208");
		} else {
			placebet.setOdds("2.000833");
		}

		return placebet;
	}

	// public static void main(String[] args) {
	// GetMark6_ZG6 test = new GetMark6_ZG6(new LoginResponse(), 45642);
	// System.out.println(test.get_placeBet());
	//
	// }
}
