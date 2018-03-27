package com.model;

import lombok.Data;

@Data
public class StartGameRequest {
	private String serialNo;
	private String sessionId;
	private String token;
}
