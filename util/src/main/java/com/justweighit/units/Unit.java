package com.justweighit.units;

public enum Unit {
	
	cups(1),
	tablespoon(2),
	teaspoon(3),
	ounces(4),
	flOz(5);
	
	private final int id;
	
	Unit(int id) {
		this.id = id;
	}
	
	public int id() {
		return id;
	}
}
