/*
 * This file is generated by jOOQ.
 */
package com.justweighit.database.jooq.tables;


import com.justweighit.database.jooq.Keys;
import com.justweighit.database.jooq.Public;
import com.justweighit.database.jooq.tables.records.NutrientRecord;

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
public class Nutrient extends TableImpl<NutrientRecord> {

    private static final long serialVersionUID = -1948691452;

    /**
     * The reference instance of <code>public.nutrient</code>
     */
    public static final Nutrient NUTRIENT = new Nutrient();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<NutrientRecord> getRecordType() {
        return NutrientRecord.class;
    }

    /**
     * The column <code>public.nutrient.id</code>.
     */
    public final TableField<NutrientRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.nutrient.name</code>.
     */
    public final TableField<NutrientRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(128), this, "");

    /**
     * The column <code>public.nutrient.unit</code>.
     */
    public final TableField<NutrientRecord, String> UNIT = createField(DSL.name("unit"), org.jooq.impl.SQLDataType.VARCHAR(32), this, "");

    /**
     * Create a <code>public.nutrient</code> table reference
     */
    public Nutrient() {
        this(DSL.name("nutrient"), null);
    }

    /**
     * Create an aliased <code>public.nutrient</code> table reference
     */
    public Nutrient(String alias) {
        this(DSL.name(alias), NUTRIENT);
    }

    /**
     * Create an aliased <code>public.nutrient</code> table reference
     */
    public Nutrient(Name alias) {
        this(alias, NUTRIENT);
    }

    private Nutrient(Name alias, Table<NutrientRecord> aliased) {
        this(alias, aliased, null);
    }

    private Nutrient(Name alias, Table<NutrientRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Nutrient(Table<O> child, ForeignKey<O, NutrientRecord> key) {
        super(child, key, NUTRIENT);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<NutrientRecord> getPrimaryKey() {
        return Keys.NUTRIENT_PKEY;
    }

    @Override
    public List<UniqueKey<NutrientRecord>> getKeys() {
        return Arrays.<UniqueKey<NutrientRecord>>asList(Keys.NUTRIENT_PKEY);
    }

    @Override
    public Nutrient as(String alias) {
        return new Nutrient(DSL.name(alias), this);
    }

    @Override
    public Nutrient as(Name alias) {
        return new Nutrient(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Nutrient rename(String name) {
        return new Nutrient(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Nutrient rename(Name name) {
        return new Nutrient(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
