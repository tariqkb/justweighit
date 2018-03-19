package com.justweighit.search.units;

import com.justweighit.units.Unit;

import java.util.List;

public class UnitWithMatchedString {
	private Unit unit;
	private List<String> matchedStrings;
	
	public UnitWithMatchedString(Unit unit, List<String> matchedStrings) {
		this.unit = unit;
		this.matchedStrings = matchedStrings;
	}
	
	public Unit getUnit() {
		return unit;
	}
	
	public List<String> getMatchedStrings() {
		return matchedStrings;
	}
}
