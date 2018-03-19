package com.justweighit.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class NutritionReport {
	
	@JsonProperty
	private String calories;
	@JsonProperty
	private double calorieRating;
	
	@JsonProperty
	private String carbs;
	@JsonProperty
	private String fat;
	@JsonProperty
	private String protein;
	@JsonProperty
	private String alcohol;
	@JsonProperty
	private String carbsPoC;
	@JsonProperty
	private String fatPoC;
	@JsonProperty
	private String proteinPoC;
	@JsonProperty
	private String alcoholPoC;
	
	@JsonProperty
	private String fiber;
	
	public void setCalories(BigDecimal calories, double rating) {
		this.calories = calories.setScale(0, BigDecimal.ROUND_DOWN).toPlainString();
		this.calorieRating = rating;
	}
	
	public void setCarbs(BigDecimal carbs, BigDecimal carbsPoC) {
		this.carbs = carbs.setScale(1, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
		this.carbsPoC = NumberFormat.getPercentInstance().format(carbsPoC.setScale(2, BigDecimal.ROUND_HALF_UP));
	}
	
	public void setFat(BigDecimal fat, BigDecimal fatPoC) {
		this.fat = fat.setScale(1, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
		this.fatPoC = NumberFormat.getPercentInstance().format(fatPoC.setScale(2, BigDecimal.ROUND_HALF_UP));
	}
	
	public void setProtein(BigDecimal protein, BigDecimal proteinPoc) {
		this.protein = protein.setScale(1, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
		this.proteinPoC = NumberFormat.getPercentInstance().format(proteinPoc.setScale(2, BigDecimal.ROUND_HALF_UP));
	}
	
	public void setAlcohol(BigDecimal alcohol, BigDecimal alcoholPoc) {
		this.alcohol = alcohol.setScale(1, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
		this.alcoholPoC = NumberFormat.getPercentInstance().format(alcoholPoc.setScale(2, BigDecimal.ROUND_HALF_UP));
	}
	
	public void setFiber(BigDecimal fiber) {
		this.fiber = fiber.setScale(0, BigDecimal.ROUND_CEILING).toPlainString();
	}
}
