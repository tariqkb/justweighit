package com.justweighit.search;

import com.justweighit.units.Unit;

public class SearchParameters {
	
	private Double amount;
	private Unit unit;
	private String foodQuery;
	
	public SearchParameters(Double amount, Unit unit, String foodQuery) {
		this.amount = amount;
		this.unit = unit;
		this.foodQuery = foodQuery;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public Unit getUnit() {
		return unit;
	}
	
	public String getFoodQuery() {
		return foodQuery;
	}
}
