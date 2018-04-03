package com.model.keno;

import java.util.List;

import lombok.Data;

@Data
public class KenoBetType {
	private String betType;
	private List<KenoBetItem> items;
}
