package com.justweighit.units;

import java.math.BigDecimal;
import java.util.Map;

import static com.justweighit.units.Unit.ounces;

public class OuncesConverter extends UnitConverter {
	
	public OuncesConverter(BigDecimal density) {
		super(density);
	}
	
	@Override
	protected void configure(Map<Unit, BigDecimal> unitConversions) {
		unitConversions.put(ounces, new BigDecimal(1.0));
	}
}
