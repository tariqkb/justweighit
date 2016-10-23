package com.justweighit.etl;

import com.justweighit.units.Unit;

import java.util.regex.Pattern;

public class UnitResolver {
	
	private final Pattern cups = Pattern.compile("^cups?(\\s|,|$).*");
	private final Pattern tablespoon = Pattern.compile("^(tablespoon|tbsp)s?(\\s|,|$).*");
	private final Pattern teapsoon = Pattern.compile("^(teaspoon|tsp)s?(\\s|,|$).*");
	private final Pattern oz = Pattern.compile("^(oz|ounces)s?(\\s|,|$).*");
	private final Pattern flOz = Pattern.compile("^(fl oz|fluid ounce)s?(\\s|,|$).*");
	
	public Unit resolve(String value) {
		
		if(cups.matcher(value).matches()) {
			return Unit.cups;
		} else if(tablespoon.matcher(value).matches()) {
			return Unit.tablespoon;
		} else if(teapsoon.matcher(value).matches()) {
			return Unit.teaspoon;
		} else if(oz.matcher(value).matches()) {
			return Unit.ounces;
		} else if(flOz.matcher(value).matches()) {
			return Unit.flOz;
		}
		
		return null;
	}
}
