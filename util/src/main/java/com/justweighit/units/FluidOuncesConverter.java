package com.justweighit.units;

import java.math.BigDecimal;
import java.util.Map;

import static com.justweighit.units.Unit.*;

public class FluidOuncesConverter extends UnitConverter {
	
	public FluidOuncesConverter(BigDecimal density) {
		super(density);
	}
	
	@Override
	protected void configure(Map<Unit, BigDecimal> unitConversions) {
		unitConversions.put(cup, new BigDecimal(8.0));
		unitConversions.put(tablespoon, new BigDecimal(0.5));
		unitConversions.put(teaspoon, new BigDecimal(1/6.0));
		unitConversions.put(flOz, new BigDecimal(1.0));
	}
}
