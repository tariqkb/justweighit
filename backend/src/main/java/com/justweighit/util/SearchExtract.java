package com.justweighit.util;

import com.justweighit.VolumeType;

public class SearchExtract {
	private double amount;
	private VolumeType unit;
	private String itemDescription;
	
	public SearchExtract(double amount, VolumeType unit, String itemDescription) {
		this.amount = amount;
		this.unit = unit;
		this.itemDescription = itemDescription;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public VolumeType getUnit() {
		return unit;
	}
	
	public String getItemDescription() {
		return itemDescription;
	}
}
