/**
 * This class is generated by jOOQ
 */
package com.justweighit.database.jooq.tables;


import com.justweighit.database.jooq.Keys;
import com.justweighit.database.jooq.Public;
import com.justweighit.database.jooq.tables.records.NutrientContentRecord;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class NutrientContent extends TableImpl<NutrientContentRecord> {

    private static final long serialVersionUID = 352607129;

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
    public final TableField<NutrientContentRecord, String> NDBNO = createField("ndbno", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false), this, "");

    /**
     * The column <code>public.nutrient_content.nutrient_id</code>.
     */
    public final TableField<NutrientContentRecord, Integer> NUTRIENT_ID = createField("nutrient_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.nutrient_content.value</code>.
     */
    public final TableField<NutrientContentRecord, BigDecimal> VALUE = createField("value", org.jooq.impl.SQLDataType.NUMERIC.nullable(false), this, "");

    /**
     * Create a <code>public.nutrient_content</code> table reference
     */
    public NutrientContent() {
        this("nutrient_content", null);
    }

    /**
     * Create an aliased <code>public.nutrient_content</code> table reference
     */
    public NutrientContent(String alias) {
        this(alias, NUTRIENT_CONTENT);
    }

    private NutrientContent(String alias, Table<NutrientContentRecord> aliased) {
        this(alias, aliased, null);
    }

    private NutrientContent(String alias, Table<NutrientContentRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<NutrientContentRecord> getPrimaryKey() {
        return Keys.NUTRIENT_CONTENT_PK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<NutrientContentRecord>> getKeys() {
        return Arrays.<UniqueKey<NutrientContentRecord>>asList(Keys.NUTRIENT_CONTENT_PK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<NutrientContentRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<NutrientContentRecord, ?>>asList(Keys.NUTRIENT_CONTENT__NUTRIENT_CONTENT_FOOD_NDBNO_FK, Keys.NUTRIENT_CONTENT__NUTRIENT_CONTENT_NUTRIENT_ID_FK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NutrientContent as(String alias) {
        return new NutrientContent(alias, this);
    }

    /**
     * Rename this table
     */
    public NutrientContent rename(String name) {
        return new NutrientContent(name, null);
    }
}