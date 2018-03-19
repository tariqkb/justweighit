package com.justweighit.units;

import java.math.BigDecimal;
import java.util.function.Function;

public enum Unit {
	
	cup(1, CupConverter::new, "cup"),
	tablespoon(2, TablespoonConverter::new, "tablespoon"),
	teaspoon(3, TeaspoonConverter::new, "teaspoon"),
	ounces(4, OuncesConverter::new, "ounce"),
	flOz(5, FluidOuncesConverter::new, "fluid ounce"),
	grams(6, GramsConverter::new, "gram"), ;
	
	private final int id;
	private final Function<BigDecimal, ? extends UnitConverter> converterFn;
	private final String prettyName;
	
	Unit(int id, Function<BigDecimal, ? extends UnitConverter> converterFn, String prettyName) {
		this.id = id;
		this.converterFn = converterFn;
		this.prettyName = prettyName;
	}
	
	public int id() {
		return id;
	}
	
	public String getPrettyName(boolean plural) {
		return plural ? prettyName + "s" : prettyName;
	}
	
	public static Unit fromId(int id) {
		for (Unit unit : values()) {
			if (unit.id == id) {
				return unit;
			}
		}
		throw new RuntimeException("Could not find unit for " + id);
	}
	
	public UnitConverter converter(BigDecimal density) {
		return converterFn.apply(density);
	}
}
