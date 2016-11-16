package com.justweighit.search;

import org.apache.commons.math3.exception.MathParseException;
import org.apache.commons.math3.fraction.FractionFormat;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AmountExtractor {
	
	private final Logger logger = Logger.getLogger(getClass().getName());
	private final Pattern amountPattern = Pattern.compile("(\\d+[\\/\\d. ]*|\\d)");
	private final FractionFormat fractionFormatter = new FractionFormat();
	
	public Double extract(String query) {
		Double amount = null;
		
		logger.info("Attempting to extract amount from '" + query + "'");
		Matcher amountMatcher = amountPattern.matcher(query);
		if (amountMatcher.find()) {
			String amountText = amountMatcher.group(1);
			logger.info("Parsing '" + amountText + "' as amount text...");
			
			try {
				amount = fractionFormatter.parse(amountText).doubleValue();
			} catch (MathParseException e) {
				logger.info("...fraction parse failed - defaulting to Double.valueOf()");
				amount = Double.valueOf(amountText);
			}
			
			logger.info("...Found '" + amount + "'");
		} else {
			logger.info("'" + query + "' did not match any amount.");
		}
		
		return amount;
	}
}
