package com.model.keno;

import lombok.Data;

@Data
public class DrawResult {
	private int id;
	private String number;
	private String result;
	private String officialResultTime;
	private String time;
}
