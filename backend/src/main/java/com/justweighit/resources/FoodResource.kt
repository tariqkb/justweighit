package com.justweighit.resources

import com.justweighit.api.FoodMeasurement
import com.justweighit.api.FoodResponse
import com.justweighit.database.jooq.Tables
import com.justweighit.database.jooq.tables.records.WeightRecord
import com.justweighit.nutrition.NutritionResource
import com.justweighit.units.GramsConverter
import com.justweighit.units.Unit
import com.justweighit.units.UnitConverter
import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.impl.DSL
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import java.util.function.Consumer
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType

@Path("/food")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class FoodResource(@Context config: Configuration?) {

    private val context: DSLContext = DSL.using(config)

    @GET
    @Path("{id}")
    fun lookup(@PathParam("id") ndbno: String,
               @QueryParam("unit") unit: Unit?,
               @QueryParam("amount") amountParam: Double?): FoodResponse {

        val food = context.fetchOne(Tables.FOOD, Tables.FOOD.NDBNO.equal(ndbno))
        val response = FoodResponse(food.description)
        val filteredConverters: MutableList<UnitConverter> = ArrayList()

        if (amountParam != null && unit != null) {
            val amount = BigDecimal.valueOf(amountParam)
            response.setAmountAndUnit(amount, unit)
            val converters = context.fetch(Tables.WEIGHT, Tables.WEIGHT.NDBNO.equal(ndbno).and(Tables.WEIGHT.UNIT.isNotNull))
                    .map { record: WeightRecord -> Unit.fromId(record.unit).converter(record.grams).setName(record.description) }
            converters.sortWith(convertersWithNamesFirst)
            val groupedConverters: MutableMap<String, MutableList<UnitConverter>> = HashMap()
            for (converter in converters) {
                groupedConverters.computeIfAbsent(converter.name) { k: String? -> ArrayList() }.add(converter)
            }
            for (convertersByName in groupedConverters.values) {
                convertersByName.stream().min(Comparator.comparing { c: UnitConverter -> normalizeConversionFactor(c, unit) }).ifPresent { unitConverter: UnitConverter ->
                    if (unitConverter.canConvertTo(unit)) {
                        filteredConverters.add(unitConverter)
                    }
                }
            }
            if (unit == Unit.grams) {
                filteredConverters.add(GramsConverter(null))
            }
            if (filteredConverters.isEmpty()) {
                getNonUnitMeasurements(ndbno).forEach(Consumer { measurement: FoodMeasurement -> response.addMeasurement(measurement) })
                response.setMessage("Couldn't find any conversions from " + unit.getPrettyName(true) + ", but this might help")
                response.setAmountAndUnit(BigDecimal.ONE, null)
            } else {
                for (converter in filteredConverters) {
                    val grams = converter.grams(unit, amount)
                    val ounces = converter.ounces(unit, amount)
                    val report = NutritionResource(context).getReport(ndbno, grams)
                    response.addMeasurement(FoodMeasurement(ounces, grams, converter.name, report))
                }
            }
        } else {
            getNonUnitMeasurements(ndbno).forEach(Consumer { measurement: FoodMeasurement -> response.addMeasurement(measurement) })
            //			response.setMessage("Couldn't find any conversions from " + unit.getPrettyName(true) + ", but this might help");
            response.setAmountAndUnit(BigDecimal.ONE, null)
        }
        return response
    }

    private fun getNonUnitMeasurements(ndbno: String): List<FoodMeasurement> {
        return context.fetch(Tables.WEIGHT, Tables.WEIGHT.NDBNO.equal(ndbno).and(Tables.WEIGHT.UNIT.isNull)).map { record: WeightRecord ->
            val grams = record.grams
            val report = NutritionResource(context).getReport(ndbno, grams)
            FoodMeasurement(grams.multiply(UnitConverter.GRAMS_TO_OUNCES), grams, record.description, report)
        }
    }

    private fun normalizeConversionFactor(converter: UnitConverter, unit: Unit): BigDecimal {
        if (!converter.canConvertTo(unit)) {
            return BigDecimal.ZERO
        }
        var conversionFactor = converter.getConversionFactor(unit)
        if (conversionFactor.compareTo(BigDecimal.ONE) < 0) {
            conversionFactor = BigDecimal.ONE.divide(conversionFactor, RoundingMode.HALF_UP)
        }
        return conversionFactor
    }

    val convertersWithNamesFirst = Comparator<UnitConverter> { c1, c2 ->
        if (c1.name == null) {
            return@Comparator -1
        } else if (c2.name == null) {
            return@Comparator 1
        }
        0
    }
}