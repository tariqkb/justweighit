/*
 * This file is generated by jOOQ.
 */
package com.justweighit.database.jooq;


import com.justweighit.database.jooq.tables.Food;
import com.justweighit.database.jooq.tables.Nutrient;
import com.justweighit.database.jooq.tables.NutrientContent;

import javax.annotation.processing.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>public</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index FOOD_PKEY = Indexes0.FOOD_PKEY;
    public static final Index NUTRIENT_PKEY = Indexes0.NUTRIENT_PKEY;
    public static final Index NUTRIENT_CONTENT_PK = Indexes0.NUTRIENT_CONTENT_PK;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index FOOD_PKEY = Internal.createIndex("food_pkey", Food.FOOD, new OrderField[] { Food.FOOD.NDBNO }, true);
        public static Index NUTRIENT_PKEY = Internal.createIndex("nutrient_pkey", Nutrient.NUTRIENT, new OrderField[] { Nutrient.NUTRIENT.ID }, true);
        public static Index NUTRIENT_CONTENT_PK = Internal.createIndex("nutrient_content_pk", NutrientContent.NUTRIENT_CONTENT, new OrderField[] { NutrientContent.NUTRIENT_CONTENT.NDBNO, NutrientContent.NUTRIENT_CONTENT.NUTRIENT_ID }, true);
    }
}