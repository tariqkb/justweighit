package com.justweighit.util;

import com.justweighit.VolumeType;

import java.util.regex.Pattern;

public class SearchExtractor {
	
	Pattern numberPattern = Pattern.compile("\\d");
	Pattern unitPattern = Pattern.compile("(cups|cup|tbsp|tbsps)");
	
	public SearchExtract extract(String text) {
		
		double amount = 0;
		VolumeType unit = null;
		String itemDescription = null;
		
		String[] tokens = text.split("\\s");
		
		for (String token : tokens) {
			if (numberPattern.matcher(token).find()) {
				amount = Double.parseDouble(token);
			} else if (unitPattern.matcher(token).find()) {
				unit = VolumeType.cup;
			} else {
				itemDescription = token;
			}
		}
		
		return new SearchExtract(amount, unit, itemDescription);
	}
}
