package com.justweighit.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.justweighit.units.Unit;

import java.math.BigDecimal;

public class FoodSearchResponse {
	
	@JsonProperty
	private String name;
	@JsonProperty
	private String ndbno;
	@JsonProperty
	private Unit unit;
	@JsonProperty
	private BigDecimal amount;
	
	public FoodSearchResponse(String ndbno, String name, Unit unit, BigDecimal amount) {
		this.ndbno = ndbno;
		this.name = name;
		this.unit = unit;
		this.amount = amount;
	}
}
