package com.justweighit.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.justweighit.units.Unit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FoodResponse {
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String amount;
	
	@JsonProperty
	private String unit;
	
	@JsonProperty
	private List<FoodMeasurement> measurements;
	
	@JsonProperty
	private String message;
	
	public FoodResponse(String name) {
		this.name = name;
		this.measurements = new ArrayList<>();
	}
	
	public void addMeasurement(FoodMeasurement measurement) {
		this.measurements.add(measurement);
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setAmountAndUnit(BigDecimal amount, Unit unit) {
		boolean plural = amount != null && amount.compareTo(BigDecimal.ONE) != 0;
		this.unit = unit != null ? unit.getPrettyName(plural) : null;
		this.amount = amount != null ? amount.setScale(2, BigDecimal.ROUND_CEILING).stripTrailingZeros().toPlainString() : null;
	}
	
}
