package com.model;

import lombok.Data;

@Data
public class nextDraw {
	private String closeTime;
	private String beginTime;
	private String endTime;
	private boolean visible;
	private int drawId;
	private String drawNumber;
	private int sequence;
	private String gameCode;
	private String market;
	private String status;
	private boolean marketClosed;
	private String marketSite;
	private int showSeq;
	private String drawDate;
}
