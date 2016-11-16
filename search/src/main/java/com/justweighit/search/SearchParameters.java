package com.justweighit.search;

import com.justweighit.units.Unit;

public class SearchParameters {
	
	private Double amount;
	private Unit unit;
	private String ndbno;
	
	public SearchParameters(Double amount, Unit unit, String ndbno) {
		this.amount = amount;
		this.unit = unit;
		this.ndbno = ndbno;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public Unit getUnit() {
		return unit;
	}
	
	public String getNdbno() {
		return ndbno;
	}
}
