/*
 * This file is generated by jOOQ.
 */
package com.justweighit.database.jooq.tables;


import com.justweighit.database.jooq.Keys;
import com.justweighit.database.jooq.Public;
import com.justweighit.database.jooq.tables.records.NutrientContentRecord;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class NutrientContent extends TableImpl<NutrientContentRecord> {

    private static final long serialVersionUID = -2106682587;

    /**
     * The reference instance of <code>public.nutrient_content</code>
     */
    public static final NutrientContent NUTRIENT_CONTENT = new NutrientContent();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<NutrientContentRecord> getRecordType() {
        return NutrientContentRecord.class;
    }

    /**
     * The column <code>public.nutrient_content.ndbno</code>.
     */
    public final TableField<NutrientContentRecord, String> NDBNO = createField(DSL.name("ndbno"), org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>public.nutrient_content.nutrient_id</code>.
     */
    public final TableField<NutrientContentRecord, Integer> NUTRIENT_ID = createField(DSL.name("nutrient_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.nutrient_content.value</code>.
     */
    public final TableField<NutrientContentRecord, BigDecimal> VALUE = createField(DSL.name("value"), org.jooq.impl.SQLDataType.NUMERIC.nullable(false), this, "");

    /**
     * Create a <code>public.nutrient_content</code> table reference
     */
    public NutrientContent() {
        this(DSL.name("nutrient_content"), null);
    }

    /**
     * Create an aliased <code>public.nutrient_content</code> table reference
     */
    public NutrientContent(String alias) {
        this(DSL.name(alias), NUTRIENT_CONTENT);
    }

    /**
     * Create an aliased <code>public.nutrient_content</code> table reference
     */
    public NutrientContent(Name alias) {
        this(alias, NUTRIENT_CONTENT);
    }

    private NutrientContent(Name alias, Table<NutrientContentRecord> aliased) {
        this(alias, aliased, null);
    }

    private NutrientContent(Name alias, Table<NutrientContentRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> NutrientContent(Table<O> child, ForeignKey<O, NutrientContentRecord> key) {
        super(child, key, NUTRIENT_CONTENT);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<NutrientContentRecord> getPrimaryKey() {
        return Keys.NUTRIENT_CONTENT_PK;
    }

    @Override
    public List<UniqueKey<NutrientContentRecord>> getKeys() {
        return Arrays.<UniqueKey<NutrientContentRecord>>asList(Keys.NUTRIENT_CONTENT_PK);
    }

    @Override
    public List<ForeignKey<NutrientContentRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<NutrientContentRecord, ?>>asList(Keys.NUTRIENT_CONTENT__NUTRIENT_CONTENT_FOOD_NDBNO_FK, Keys.NUTRIENT_CONTENT__NUTRIENT_CONTENT_NUTRIENT_ID_FK);
    }

    public Food food() {
        return new Food(this, Keys.NUTRIENT_CONTENT__NUTRIENT_CONTENT_FOOD_NDBNO_FK);
    }

    public Nutrient nutrient() {
        return new Nutrient(this, Keys.NUTRIENT_CONTENT__NUTRIENT_CONTENT_NUTRIENT_ID_FK);
    }

    @Override
    public NutrientContent as(String alias) {
        return new NutrientContent(DSL.name(alias), this);
    }

    @Override
    public NutrientContent as(Name alias) {
        return new NutrientContent(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public NutrientContent rename(String name) {
        return new NutrientContent(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public NutrientContent rename(Name name) {
        return new NutrientContent(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<String, Integer, BigDecimal> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
