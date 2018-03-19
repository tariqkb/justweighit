package com.justweighit.nutrition;

import com.justweighit.api.NutritionReport;
import org.jooq.DSLContext;

import java.math.BigDecimal;
import java.util.Map;

import static com.justweighit.database.jooq.Tables.NUTRIENT;
import static com.justweighit.database.jooq.Tables.NUTRIENT_CONTENT;
import static com.justweighit.nutrition.Nutrient.*;

public class NutritionResource {
	
	private final DSLContext context;
	
	public NutritionResource(DSLContext context) {
		this.context = context;
	}
	
	public NutritionReport getReport(String ndbno, BigDecimal grams) {
		NutritionReport report = new NutritionReport();
		
		Map<Nutrient, BigDecimal> nutrients = getNutrients(ndbno, Nutrient.values());
		
		BigDecimal calories = nutrients.get(energy).multiply(grams);
		
		BigDecimal carbs = nutrients.get(carbohydrates).multiply(grams);
		BigDecimal protein = nutrients.get(proteins).multiply(grams);
		BigDecimal fat = nutrients.get(lipids).multiply(grams);
		BigDecimal alcohol = nutrients.get(alchohol).multiply(grams);
		
		BigDecimal carbsCals = carbs.multiply(BigDecimal.valueOf(4));
		BigDecimal proteinCals = protein.multiply(BigDecimal.valueOf(4));
		BigDecimal fatCals = fat.multiply(BigDecimal.valueOf(9));
		BigDecimal alcoholCals = alcohol.multiply(BigDecimal.valueOf(7));
		
		BigDecimal estimatedCals = carbsCals.add(proteinCals).add(fatCals).add(alcoholCals);
		
		if (calories.compareTo(BigDecimal.ZERO) == 0 || estimatedCals.compareTo(BigDecimal.ZERO) == 0) {
			report.setCarbs(carbs, BigDecimal.ZERO);
			report.setFat(fat, BigDecimal.ZERO);
			report.setProtein(protein, BigDecimal.ZERO);
			report.setAlcohol(alcohol, BigDecimal.ZERO);
			report.setFiber(nutrients.get(fiber));
		} else {
			
			BigDecimal calorieCorrection = calories.divide(estimatedCals, BigDecimal.ROUND_UP);
			
			report.setCarbs(carbs, carbsCals.multiply(calorieCorrection).divide(calories, BigDecimal.ROUND_HALF_UP));
			report.setFat(fat, fatCals.multiply(calorieCorrection).divide(calories, BigDecimal.ROUND_HALF_UP));
			report.setProtein(protein, proteinCals.multiply(calorieCorrection).divide(calories, BigDecimal.ROUND_HALF_UP));
			report.setAlcohol(alcohol, alcoholCals.multiply(calorieCorrection).divide(calories, BigDecimal.ROUND_HALF_UP));
			report.setFiber(nutrients.get(fiber));
		}
		
		report.setCalories(calories, 1);
		
		return report;
	}
	
	/**
	 * Gets a nutrient map for a given food. Mass values are normalized to grams and correspond to 1 gram of given food (instead of ndb 100g)
	 *
	 * @param ndbno
	 *
	 * @return
	 */
	public Map<Nutrient, BigDecimal> getNutrients(String ndbno, Nutrient... nutrients) {
		return context.select().from(NUTRIENT_CONTENT)
			.join(NUTRIENT).on(NUTRIENT.ID.eq(NUTRIENT_CONTENT.NUTRIENT_ID))
			.where(NUTRIENT_CONTENT.NDBNO.equal(ndbno).and(Nutrient.cond(nutrients)))
			.fetch().intoMap(r -> Nutrient.fromId(r.getValue(NUTRIENT.ID)),
				r -> r.getValue(NUTRIENT_CONTENT.VALUE).divide(BigDecimal.valueOf(100), BigDecimal.ROUND_HALF_UP));
	}
}
