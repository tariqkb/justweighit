package com.justweighit.search;

import com.justweighit.search.units.UnitSearcher;
import com.justweighit.units.Unit;

import java.util.List;

public class SearchParser {
	
	private final UnitSearcher unitSearcher;
	private final AmountExtractor amountExtractor;
	
	public SearchParser(UnitSearcher unitSearcher, AmountExtractor amountExtractor) {
		this.unitSearcher = unitSearcher;
		this.amountExtractor = amountExtractor;
	}
	
	public SearchParameters parse(String query) {
		Double amount = amountExtractor.extract(query);
		Unit unit = null;
		
		List<Unit> units = unitSearcher.run(query);
		if(!units.isEmpty()) {
			unit = units.get(0);
		}
		
		String ndbno = null;
		
		
		return new SearchParameters(amount, unit, ndbno);
	}
}
