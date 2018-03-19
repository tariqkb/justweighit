package com.justweighit.search;

import com.justweighit.units.Unit;

import java.math.BigDecimal;
import java.util.List;

public class SearchParameters {
	
	private final List<NDBSearcherResult> ndbnos;
	private final BigDecimal amount;
	private final Unit unit;
	
	public SearchParameters(BigDecimal amount, Unit unit, List<NDBSearcherResult> ndbnos) {
		this.amount = amount;
		this.unit = unit;
		this.ndbnos = ndbnos;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public Unit getUnit() {
		return unit;
	}
	
	public List<NDBSearcherResult> getNdbnos() {
		return ndbnos;
	}
}
