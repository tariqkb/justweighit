package com.justweighit.search;

import java.math.BigDecimal;

public class AmountWithMatchedString {
	
	private BigDecimal amount;
	private String matchedString;
	
	public AmountWithMatchedString(BigDecimal amount, String matchedString) {
		this.amount = amount;
		this.matchedString = matchedString;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public String getMatchedString() {
		return matchedString;
	}
}
