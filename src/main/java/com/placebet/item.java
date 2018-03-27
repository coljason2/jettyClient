package com.placebet;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class item {
	private List<String> betItem = new ArrayList<String>();
	private String drawType;
	private List<String> betAmount = new ArrayList<String>();
}
