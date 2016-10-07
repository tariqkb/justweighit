package com.justweighit.api;

import com.justweighit.VolumeType;

public class Volume {
	
	private double amount;
	private VolumeType type;
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public VolumeType getType() {
		return type;
	}
	
	public void setType(VolumeType type) {
		this.type = type;
	}
}
