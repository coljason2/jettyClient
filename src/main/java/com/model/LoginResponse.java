package com.model;

import lombok.Data;

@Data
public class LoginResponse {
	private acct acct;
	private String sessionId;
	private String token;
	private String code;
	private String serialNo;
}
