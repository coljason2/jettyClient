package com.GenIlottoGame.HL11x5;

import java.util.Map;
import java.util.Random;

import com.GenIlottoGame.AbstractGeniLottoGame;
import com.google.common.collect.ImmutableMap;
import com.model.Request.LoginResponse;

public class HL11x5AllGames {

	private LoginResponse loginRsp;
	private int drawId;
	private Map<Integer, AbstractGeniLottoGame> PlaceBet;

	public HL11x5AllGames(LoginResponse loginRsp, int drawId) {
		this.loginRsp = loginRsp;
		this.drawId = drawId;
		initializeAllGame();
	}

	public void initializeAllGame() {
		PlaceBet = new ImmutableMap.Builder<Integer, AbstractGeniLottoGame>()
				.put(0, new GetHL11x5_N3PH(loginRsp, drawId)).put(1, new GetHLx5_G3H(loginRsp, drawId)).build();
	}

	public AbstractGeniLottoGame getGame() {
		return PlaceBet.get(1);
	}
}
