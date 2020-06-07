package com.justweighit.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.justweighit.units.Unit
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class FoodResponse(@field:JsonProperty private val name: String) {
    
    @JsonProperty
    private var amount: String? = null
    @JsonProperty
    private var unit: String? = null
    @JsonProperty
    private val measurements: MutableList<FoodMeasurement>
    @JsonProperty
    private var message: String? = null

    fun addMeasurement(measurement: FoodMeasurement) {
        measurements.add(measurement)
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun setAmountAndUnit(amount: BigDecimal?, unit: Unit?) {
        val plural = amount?.compareTo(BigDecimal.ONE) != 0
        this.unit = unit?.getPrettyName(plural)
        this.amount = amount?.setScale(2, RoundingMode.CEILING)?.stripTrailingZeros()?.toPlainString()
    }

    init {
        measurements = ArrayList()
    }
}