package com.placebet;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class placeBetEntity {

	private List<item> items = new ArrayList<item>();
	private String gameCode;
	private String market;
	private String betType;
	private String prizeGroup;
	private String serialNo;
	private String sessionId;
	private String token;
	private int drawId;
	private int multiple;
	private int betUnit;
	private int betCount;
	private int betAmount;
	private double comm;
	private String odds;
}
