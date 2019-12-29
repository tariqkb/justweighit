/*
 * This file is generated by jOOQ.
 */
package com.justweighit.database.jooq.tables.records;


import com.justweighit.database.jooq.tables.NutrientContent;

import java.math.BigDecimal;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class NutrientContentRecord extends UpdatableRecordImpl<NutrientContentRecord> implements Record3<String, Integer, BigDecimal> {

    private static final long serialVersionUID = -1291721311;

    /**
     * Setter for <code>public.nutrient_content.ndbno</code>.
     */
    public NutrientContentRecord setNdbno(String value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.nutrient_content.ndbno</code>.
     */
    public String getNdbno() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.nutrient_content.nutrient_id</code>.
     */
    public NutrientContentRecord setNutrientId(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.nutrient_content.nutrient_id</code>.
     */
    public Integer getNutrientId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.nutrient_content.value</code>.
     */
    public NutrientContentRecord setValue(BigDecimal value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.nutrient_content.value</code>.
     */
    public BigDecimal getValue() {
        return (BigDecimal) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<String, Integer> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<String, Integer, BigDecimal> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<String, Integer, BigDecimal> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return NutrientContent.NUTRIENT_CONTENT.NDBNO;
    }

    @Override
    public Field<Integer> field2() {
        return NutrientContent.NUTRIENT_CONTENT.NUTRIENT_ID;
    }

    @Override
    public Field<BigDecimal> field3() {
        return NutrientContent.NUTRIENT_CONTENT.VALUE;
    }

    @Override
    public String component1() {
        return getNdbno();
    }

    @Override
    public Integer component2() {
        return getNutrientId();
    }

    @Override
    public BigDecimal component3() {
        return getValue();
    }

    @Override
    public String value1() {
        return getNdbno();
    }

    @Override
    public Integer value2() {
        return getNutrientId();
    }

    @Override
    public BigDecimal value3() {
        return getValue();
    }

    @Override
    public NutrientContentRecord value1(String value) {
        setNdbno(value);
        return this;
    }

    @Override
    public NutrientContentRecord value2(Integer value) {
        setNutrientId(value);
        return this;
    }

    @Override
    public NutrientContentRecord value3(BigDecimal value) {
        setValue(value);
        return this;
    }

    @Override
    public NutrientContentRecord values(String value1, Integer value2, BigDecimal value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached NutrientContentRecord
     */
    public NutrientContentRecord() {
        super(NutrientContent.NUTRIENT_CONTENT);
    }

    /**
     * Create a detached, initialised NutrientContentRecord
     */
    public NutrientContentRecord(String ndbno, Integer nutrientId, BigDecimal value) {
        super(NutrientContent.NUTRIENT_CONTENT);

        set(0, ndbno);
        set(1, nutrientId);
        set(2, value);
    }
}
