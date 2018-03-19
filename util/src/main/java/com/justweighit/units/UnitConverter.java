package com.justweighit.units;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public abstract class UnitConverter {
	
	public static final java.math.BigDecimal GRAMS_TO_OUNCES = new BigDecimal(0.035274);
	private Map<Unit, BigDecimal> unitConversions;
	private BigDecimal density;
	private String name;
	
	public UnitConverter(BigDecimal density) {
		this.density = density;
		this.unitConversions = new HashMap<>();
		this.configure(this.unitConversions);
	}
	
	/**
	 * Configures unit conversions. Describes how other units can be converted to this unitConverter's unit.
	 *
	 * Example: If this unitConverter is for tablespoon -> unitConversions.put(teaspoon, 1/3.0)
	 * @param unitConversions
	 */
	protected abstract void configure(Map<Unit, BigDecimal> unitConversions);
	
	public boolean canConvertTo(Unit unit) {
		return unitConversions.containsKey(unit);
	}
	
	public BigDecimal getConversionFactor(Unit unit) {
		return unitConversions.get(unit);
	}
	
	public BigDecimal grams(Unit unit, BigDecimal amount) {
		
		return amount.multiply(unitConversions.get(unit).multiply(density)).setScale(0, BigDecimal.ROUND_HALF_UP);
	}
	
	public BigDecimal ounces(Unit unit, BigDecimal amount) {
	
		return grams(unit, amount).multiply(GRAMS_TO_OUNCES);
	}
	
	public UnitConverter setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getName() {
		return name;
	}
}
