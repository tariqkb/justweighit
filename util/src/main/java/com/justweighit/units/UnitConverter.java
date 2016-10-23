package com.justweighit.units;

public class UnitConverter {
	private double density;
	
	public UnitConverter(double amount, double grams) {
		this.density = grams / amount;
	}
	
	public double grams(double amount) {
		return amount * density;
	}
}
