package com.justweighit.units;

import java.math.BigDecimal;
import java.util.Map;

import static com.justweighit.units.Unit.*;

public class CupConverter extends UnitConverter {
	
	public CupConverter(BigDecimal density) {
		super(density);
	}
	
	@Override
	protected void configure(Map<Unit, BigDecimal> unitConversions) {
		unitConversions.put(cup, new BigDecimal(1.0));
		unitConversions.put(tablespoon, new BigDecimal(1/16.0));
		unitConversions.put(teaspoon, new BigDecimal(1/48.0));
		unitConversions.put(flOz, new BigDecimal(1/8.0));
	}
}
