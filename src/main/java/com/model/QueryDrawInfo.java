package com.model;

import lombok.Data;

@Data
public class QueryDrawInfo extends StartGameRequest {
	private String market;
	private String gameCode;

}
