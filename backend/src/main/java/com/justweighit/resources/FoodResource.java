package com.justweighit.resources;

import com.justweighit.api.FoodMeasurement;
import com.justweighit.api.FoodResponse;
import com.justweighit.api.NutritionReport;
import com.justweighit.database.jooq.tables.records.FoodRecord;
import com.justweighit.database.jooq.tables.records.WeightRecord;
import com.justweighit.nutrition.NutritionResource;
import com.justweighit.units.GramsConverter;
import com.justweighit.units.Unit;
import com.justweighit.units.UnitConverter;
import io.progix.dropwizard.jooq.JooqConfiguration;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.impl.DSL;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.justweighit.database.jooq.Tables.FOOD;
import static com.justweighit.database.jooq.Tables.WEIGHT;
import static com.justweighit.units.UnitConverter.GRAMS_TO_OUNCES;

@Path("/food")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FoodResource {
	
	private final DSLContext context;
	
	public FoodResource(@JooqConfiguration Configuration config) {
		this.context = DSL.using(config);
	}
	
	@GET
	@Path("{id}")
	public FoodResponse lookup(@PathParam("id") String ndbno, @QueryParam("unit") Unit unit, @QueryParam("amount") Double amountParam) {
		FoodRecord food = context.fetchOne(FOOD, FOOD.NDBNO.equal(ndbno));
		
		FoodResponse response = new FoodResponse(food.getDescription());
		
		List<UnitConverter> filteredConverters = new ArrayList<>();
		
		if (amountParam != null && unit != null) {
			BigDecimal amount = BigDecimal.valueOf(amountParam);
			response.setAmountAndUnit(amount, unit);
			
			List<UnitConverter> converters = context.fetch(WEIGHT, WEIGHT.NDBNO.equal(ndbno).and(WEIGHT.UNIT.isNotNull()))
				.map(record -> Unit.fromId(record.getUnit()).converter(record.getGrams()).setName(record.getDescription()));
			converters.sort((c1, c2) -> {
				if (c1.getName() == null) {
					return -1;
				} else if (c2.getName() == null) {
					return 1;
				}
				
				return 0;
			});
			
			Map<String, List<UnitConverter>> groupedConverters = new HashMap<>();
			for (UnitConverter converter : converters) {
				groupedConverters.computeIfAbsent(converter.getName(), k -> new ArrayList<>()).add(converter);
			}
			
			for (List<UnitConverter> convertersByName : groupedConverters.values()) {
				convertersByName.stream().min(Comparator.comparing(c -> normalizeConversionFactor(c, unit))).ifPresent(unitConverter -> {
					if (unitConverter.canConvertTo(unit)) {
						filteredConverters.add(unitConverter);
					}
				});
			}
			
			if (unit == Unit.grams) {
				filteredConverters.add(new GramsConverter(null));
			}
			
			if (filteredConverters.isEmpty()) {
				
				getNonUnitMeasurements(ndbno).forEach(response::addMeasurement);
				response.setMessage("Couldn't find any conversions from " + unit.getPrettyName(true) + ", but this might help");
				response.setAmountAndUnit(BigDecimal.ONE, null);
			} else {
				
				for (UnitConverter converter : filteredConverters) {
					BigDecimal grams = converter.grams(unit, amount);
					BigDecimal ounces = converter.ounces(unit, amount);
					
					NutritionReport report = new NutritionResource(context).getReport(ndbno, grams);
					response.addMeasurement(new FoodMeasurement(ounces, grams, converter.getName(), report));
				}
			}
		} else {
			
			getNonUnitMeasurements(ndbno).forEach(response::addMeasurement);
//			response.setMessage("Couldn't find any conversions from " + unit.getPrettyName(true) + ", but this might help");
			response.setAmountAndUnit(BigDecimal.ONE, null);
		}
		
		return response;
	}
	
	private List<FoodMeasurement> getNonUnitMeasurements(String ndbno) {
		return context.fetch(WEIGHT, WEIGHT.NDBNO.equal(ndbno).and(WEIGHT.UNIT.isNull())).map(record -> {
			BigDecimal grams = record.getGrams();
			NutritionReport report = new NutritionResource(context).getReport(ndbno, grams);
			return new FoodMeasurement(grams.multiply(GRAMS_TO_OUNCES), grams, record.getDescription(), report);
		});
	}
	
	private BigDecimal normalizeConversionFactor(UnitConverter converter, Unit unit) {
		if (!converter.canConvertTo(unit)) {
			return BigDecimal.ZERO;
		}
		
		BigDecimal conversionFactor = converter.getConversionFactor(unit);
		if (conversionFactor.compareTo(BigDecimal.ONE) < 0) {
			conversionFactor = BigDecimal.ONE.divide(conversionFactor, RoundingMode.HALF_UP);
		}
		
		return conversionFactor;
	}
}
