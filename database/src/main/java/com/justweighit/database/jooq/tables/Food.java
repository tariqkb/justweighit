/**
 * This class is generated by jOOQ
 */
package com.justweighit.database.jooq.tables;


import com.justweighit.database.jooq.Keys;
import com.justweighit.database.jooq.Public;
import com.justweighit.database.jooq.tables.records.FoodRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Food extends TableImpl<FoodRecord> {

    private static final long serialVersionUID = -1855834294;

    /**
     * The reference instance of <code>public.food</code>
     */
    public static final Food FOOD = new Food();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<FoodRecord> getRecordType() {
        return FoodRecord.class;
    }

    /**
     * The column <code>public.food.ndbno</code>.
     */
    public final TableField<FoodRecord, String> NDBNO = createField("ndbno", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false), this, "");

    /**
     * The column <code>public.food.description</code>.
     */
    public final TableField<FoodRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(1024), this, "");

    /**
     * The column <code>public.food.short_description</code>.
     */
    public final TableField<FoodRecord, String> SHORT_DESCRIPTION = createField("short_description", org.jooq.impl.SQLDataType.VARCHAR.length(256), this, "");

    /**
     * Create a <code>public.food</code> table reference
     */
    public Food() {
        this("food", null);
    }

    /**
     * Create an aliased <code>public.food</code> table reference
     */
    public Food(String alias) {
        this(alias, FOOD);
    }

    private Food(String alias, Table<FoodRecord> aliased) {
        this(alias, aliased, null);
    }

    private Food(String alias, Table<FoodRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<FoodRecord> getPrimaryKey() {
        return Keys.FOOD_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<FoodRecord>> getKeys() {
        return Arrays.<UniqueKey<FoodRecord>>asList(Keys.FOOD_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Food as(String alias) {
        return new Food(alias, this);
    }

    /**
     * Rename this table
     */
    public Food rename(String name) {
        return new Food(name, null);
    }
}
