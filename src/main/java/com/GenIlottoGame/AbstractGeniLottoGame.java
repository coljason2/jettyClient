package com.GenIlottoGame;

import java.util.HashSet;
import java.util.Random;

import com.api.ChineseCalendar;
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
	public HashSet<Integer> RndNum = new HashSet<Integer>();
	public item item;
	public static final String ThisYearIndex = String.valueOf(ChineseCalendar.getAnimalIndex() + 1);

	abstract public placeBetEntity get_placeBet();

	public void createRandomNumber(int RandomSize, int seed) {
		while (RndNum.size() < RandomSize) {
			int Random = new Random().nextInt(seed) + 1;
			RndNum.add(Random);
		}
	};
}
