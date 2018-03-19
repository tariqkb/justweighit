package com.justweighit.etl;

import com.justweighit.units.Unit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnitResolver {
	
	private final Pattern cups = Pattern.compile("^cups?(\\s|,|$)(.*)");
	private final Pattern tablespoon = Pattern.compile("^(tablespoon|tbsp)s?(\\s|,|$)(.*)");
	private final Pattern teapsoon = Pattern.compile("^(teaspoon|tsp)s?(\\s|,|$)(.*)");
	private final Pattern oz = Pattern.compile("^(oz|ounces)s?(\\s|,|$)(.*)");
	private final Pattern flOz = Pattern.compile("^(fl oz|fluid ounce)s?(\\s|,|$)(.*)");
	
	public UnitWithDescription resolve(String value) {
		
		Unit unit = null;
		String description = value.trim();
		Matcher cupMatcher = cups.matcher(value);
		Matcher tbspMatcher = tablespoon.matcher(value);
		Matcher tspMatcher = teapsoon.matcher(value);
		Matcher ozMatcher = oz.matcher(value);
		Matcher flozMatcher = flOz.matcher(value);
		
		if (cupMatcher.matches()) {
			unit = Unit.cup;
			description = cupMatcher.group(2);
		} else if (tbspMatcher.matches()) {
			unit = Unit.tablespoon;
			description = tbspMatcher.group(3);
		} else if (tspMatcher.matches()) {
			unit = Unit.teaspoon;
			description = tspMatcher.group(3);
		} else if (ozMatcher.matches()) {
			unit = Unit.ounces;
			description = ozMatcher.group(3);
		} else if (flozMatcher.matches()) {
			unit = Unit.flOz;
			description = flozMatcher.group(3);
		}
		
		return new UnitWithDescription(unit, description.isEmpty() ? null : description);
	}
	
	public static class UnitWithDescription {
		
		private Unit unit;
		private String description;
		
		public UnitWithDescription(Unit unit, String description) {
			this.unit = unit;
			this.description = description != null ? description.trim() : null;
		}
		
		public Unit getUnit() {
			return unit;
		}
		
		public String getDescription() {
			return description;
		}
	}
}
