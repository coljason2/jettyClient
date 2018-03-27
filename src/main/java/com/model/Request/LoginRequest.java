package com.model.Request;

import lombok.Data;

@Data
public class LoginRequest {

	private String acctId = "test_player";
	private String passwd = "000000";
	private String sessionId;
	private String token;
	private String serialNo;

}
