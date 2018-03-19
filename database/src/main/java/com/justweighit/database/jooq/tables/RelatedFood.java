/**
 * This class is generated by jOOQ
 */
package com.justweighit.database.jooq.tables;


import com.justweighit.database.jooq.Public;
import com.justweighit.database.jooq.tables.records.RelatedFoodRecord;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
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
public class RelatedFood extends TableImpl<RelatedFoodRecord> {

    private static final long serialVersionUID = 1726632143;

    /**
     * The reference instance of <code>public.related_food</code>
     */
    public static final RelatedFood RELATED_FOOD = new RelatedFood();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RelatedFoodRecord> getRecordType() {
        return RelatedFoodRecord.class;
    }

    /**
     * The column <code>public.related_food.description</code>.
     */
    public final TableField<RelatedFoodRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false), this, "");

    /**
     * The column <code>public.related_food.ndbno</code>.
     */
    public final TableField<RelatedFoodRecord, String> NDBNO = createField("ndbno", org.jooq.impl.SQLDataType.CHAR.length(128).nullable(false), this, "");

    /**
     * The column <code>public.related_food.name</code>.
     */
    public final TableField<RelatedFoodRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false), this, "");

    /**
     * Create a <code>public.related_food</code> table reference
     */
    public RelatedFood() {
        this("related_food", null);
    }

    /**
     * Create an aliased <code>public.related_food</code> table reference
     */
    public RelatedFood(String alias) {
        this(alias, RELATED_FOOD);
    }

    private RelatedFood(String alias, Table<RelatedFoodRecord> aliased) {
        this(alias, aliased, null);
    }

    private RelatedFood(String alias, Table<RelatedFoodRecord> aliased, Field<?>[] parameters) {
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
    public RelatedFood as(String alias) {
        return new RelatedFood(alias, this);
    }

    /**
     * Rename this table
     */
    public RelatedFood rename(String name) {
        return new RelatedFood(name, null);
    }
}