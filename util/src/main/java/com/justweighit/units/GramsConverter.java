package com.justweighit.units;

import java.math.BigDecimal;
import java.util.Map;

public class GramsConverter extends UnitConverter {
	
	public GramsConverter(BigDecimal density) {
		super(new BigDecimal(1));
	}
	
	@Override
	protected void configure(Map<Unit, BigDecimal> unitConversions) {
		unitConversions.put(Unit.grams, new BigDecimal(1));
	}
}
