package com.GenIlottoGame;

import com.placebet.item;
import com.placebet.placeBetEntity;

/*
 *
 * ilotto game abstract class
 * 
 * 
 * */
public abstract class AbstractGeniLottoGame {
	public placeBetEntity placebet = new placeBetEntity();
	public item item;

	abstract public placeBetEntity get_placeBet();
}
