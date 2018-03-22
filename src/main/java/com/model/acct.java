package com.model;

import lombok.Data;

@Data
public class acct {
	private String acctId;
	private String currency;
	private String acctName;
	private String merchant;
	private String balance;
}
