package com.GenIlottoGame.mark6;

import java.util.Map;
import com.GenIlottoGame.AbstractGeniLottoGame;
import com.google.common.collect.ImmutableMap;
import com.model.Request.LoginResponse;

public class Mark6AllGames {
	private LoginResponse loginRsp;
	private int drawId;
	private Map<Integer, AbstractGeniLottoGame> PlaceBet;

	public Mark6AllGames(LoginResponse loginRsp, int drawId) {
		this.loginRsp = loginRsp;
		this.drawId = drawId;
		initializeAllGame();
	}

	public void initializeAllGame() {
		PlaceBet = new ImmutableMap.Builder<Integer, AbstractGeniLottoGame>()
				.put(0, new GetMark6_HALF(loginRsp, drawId)).put(1, new GetMark6_M6N1_6(loginRsp, drawId))
				.put(2, new GetMark6_M6N7(loginRsp, drawId)).put(3, new GetMark6_M6T(loginRsp, drawId))
				.put(4, new GetMark6_NO_B(loginRsp, drawId)).put(5, new GetMark6_TAIL(loginRsp, drawId))
				.put(6, new GetMark6_ZALL(loginRsp, drawId)).put(7, new GetMark6_ZG6(loginRsp, drawId))
				.put(8, new GetMark6_ZN7(loginRsp, drawId)).build();
	}

	public AbstractGeniLottoGame getGame(int index) {
		return PlaceBet.get(index);
	}

//	public static void main(String[] args) {
//		Mark6AllGames Mark6AllGames = new Mark6AllGames(new LoginResponse(), 5566);
//
//		System.out.println(Mark6AllGames.getGame(4).get_placeBet());
//	}
}
