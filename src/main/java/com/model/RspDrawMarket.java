package com.model;

import java.util.List;

import lombok.Data;

@Data
public class RspDrawMarket {
	private String beginTime;
	private String closeTime;
	private String drawDate;
	private int drawId;
	private String drawNumber;
	private String endTime;
	private String gameCode;
	private List<Integer> latestDraws;
	private String market;
	private boolean marketClosed;
	private String marketSite;
	private nextDraw nextDraw;
	private int sequence;
	private int showSeq;
	private String status;
	private boolean visible;
}
