package com.model;

import com.model.Request.StartGameRequest;

import lombok.Data;

@Data
public class QueryDrawInfo extends StartGameRequest {
	private String market;
	private String gameCode;
}
