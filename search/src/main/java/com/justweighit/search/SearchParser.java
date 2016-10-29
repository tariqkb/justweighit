package com.justweighit.search;

import com.justweighit.units.Unit;
import org.apache.commons.math3.fraction.FractionFormat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchParser {

	private final Pattern amountPattern = Pattern.compile("(\\d+[\\/\\d. ]*|\\d)");
	private final Pattern unitPattern = Pattern.compile("(?:(teaspoon|tsp)|(teaspoon|tsp))s?");
	private final FractionFormat fractionFormatter = new FractionFormat();
	
	public SearchParameters parse(String query) {
		Double amount = null;
		Unit unit = null;
		
		Matcher amountMatcher = amountPattern.matcher(query);
		if(amountMatcher.matches()) {
			String amountText = amountMatcher.group(0);
			amount = fractionFormatter.parse(amountText).doubleValue();
		}
		
//		Pattern.
//		amountPattern.asPredicate().
		
		return new SearchParameters(amount, unit, query);
	}
}
