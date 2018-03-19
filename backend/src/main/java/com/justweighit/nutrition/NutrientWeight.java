package com.justweighit.nutrition;

public enum NutrientWeight {
	
	µg(1/1000000.0), mg(1/1000.0), g(1.0), kcal, kJ, IU;
	
	private final Double gramFactor;
	
	NutrientWeight(double gramFactor) {
		this.gramFactor = gramFactor;
	}
	
	NutrientWeight() {
		this.gramFactor = null;
	}
	
	public Double getGramFactor() {
		return this.gramFactor;
	}
}
