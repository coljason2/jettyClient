package com.model.Request;

import com.model.acct;

import lombok.Data;

@Data
public class LoginResponse {
	private acct acct;
	private String sessionId;
	private String token;
	private String code;
	private String serialNo;
}
