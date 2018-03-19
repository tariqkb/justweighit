package com.justweighit.units;

import java.math.BigDecimal;
import java.util.Map;

import static com.justweighit.units.Unit.*;

public class TablespoonConverter extends UnitConverter {
	
	public TablespoonConverter(BigDecimal density) {
		super(density);
	}
	
	@Override
	protected void configure(Map<Unit, BigDecimal> unitConversions) {
		unitConversions.put(cup, new BigDecimal(16.0));
		unitConversions.put(tablespoon, new BigDecimal(1.0));
		unitConversions.put(teaspoon, new BigDecimal(1/3.0));
		unitConversions.put(flOz, new BigDecimal(2.0));
	}
}
