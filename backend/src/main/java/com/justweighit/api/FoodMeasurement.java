package com.justweighit.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_DOWN;

public class FoodMeasurement {
	
	@JsonProperty
	private String ounces;
	@JsonProperty
	private String grams;
	@JsonProperty
	private String description;
	
	@JsonProperty
	private NutritionReport nutrition;
	
	public FoodMeasurement(BigDecimal ounces, BigDecimal grams, String description, NutritionReport nutrition) {
		this.ounces = ounces.setScale(2, ROUND_DOWN).stripTrailingZeros().toPlainString();
		this.grams = grams.setScale(2, ROUND_DOWN).stripTrailingZeros().toPlainString();
		this.description = description;
		this.nutrition = nutrition;
	}
}
