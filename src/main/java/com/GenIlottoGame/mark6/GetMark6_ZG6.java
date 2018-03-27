package com.GenIlottoGame.mark6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.GenIlottoGame.AbstractGeniLottoGame;
import com.google.common.collect.ImmutableMap;
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

	public GetMark6_ZG6(String serialNo, String sessionId, String token, int drawId) {
		super();
		placebet.setSerialNo(serialNo);
		placebet.setSessionId(sessionId);
		placebet.setToken(token);
		placebet.setDrawId(drawId);
		placebet.setMarket("HK");
		placebet.setGameCode("MARK6");
		placebet.setMultiple(1);
		placebet.setComm(0.02);
		placebet.setPrizeGroup("1950");
		placebet.setBetType("ZG6");
		placebet.setOdds("2.000833");
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
		while (item.getBetItem().size() < size) {
			int Random = new Random().nextInt(11) + 1;
			if (!item.getBetItem().contains(Random + "")) {
				item.getBetItem().add(Random + "");
			}
		}
		placebet.setBetUnit(betuint);
		placebet.setBetAmount(betuint * countBetcount(size));
		placebet.setBetCount(countBetcount(size));
		placebet.getItems().add(item);
		return placebet;
	}

	// public static void main(String[] args) {
	// GetMark6_ZG6 test = new GetMark6_ZG6("ddddd", "dddddd", "dddddd", 45642);
	// System.out.println(test.get_placeBet());
	//
	// }
}
