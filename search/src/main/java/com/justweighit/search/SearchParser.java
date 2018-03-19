package com.justweighit.search;

import com.justweighit.search.units.UnitSearcher;
import com.justweighit.search.units.UnitWithMatchedString;
import com.justweighit.units.Unit;

import java.math.BigDecimal;
import java.util.List;

public class SearchParser {
	
	private final UnitSearcher unitSearcher;
	private final AmountExtractor amountExtractor;
	private final NDBSearcher ndbSearcher;
	
	public SearchParser(UnitSearcher unitSearcher, AmountExtractor amountExtractor, NDBSearcher ndbSearcher) {
		this.unitSearcher = unitSearcher;
		this.amountExtractor = amountExtractor;
		this.ndbSearcher = ndbSearcher;
	}
	
	public SearchParameters parse(String query) {
		String foodQueryPart = query;
		
		AmountWithMatchedString amountWithMatchedString = amountExtractor.extract(query);
		foodQueryPart = foodQueryPart.replaceAll(amountWithMatchedString.getMatchedString(), "");
		
		Unit unit = null;
		
		List<UnitWithMatchedString> units = unitSearcher.run(query);
		if(!units.isEmpty()) {
			unit = units.get(0).getUnit();
			
			for (String s : units.get(0).getMatchedStrings()) {
				foodQueryPart = foodQueryPart.replaceAll(s, "");
			}
		}
		
		List<NDBSearcherResult> ndbnos = ndbSearcher.findNdbno(foodQueryPart);
		
		return new SearchParameters(amountWithMatchedString.getAmount(), unit, ndbnos);
	}
}
