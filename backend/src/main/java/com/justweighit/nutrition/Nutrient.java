package com.justweighit.nutrition;

import org.jooq.Condition;
import org.jooq.impl.DSL;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.justweighit.database.jooq.Tables.NUTRIENT_CONTENT;

public enum Nutrient {
	
	carbohydrates(205),
	lipids(204),
	proteins(203),
	water(255),
	ash(207),
	energy(208),
	alchohol(221),
	fiber(291);
	
	private final int id;
	
	Nutrient(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public static Condition cond(Nutrient... nutrients) {
		return NUTRIENT_CONTENT.NUTRIENT_ID.in(Arrays.stream(nutrients).map(Nutrient::getId).collect(Collectors.toList()));
	}
	
	public static Nutrient fromId(int nutrientId) {
		return Arrays.stream(values()).filter(nutrient -> nutrient.getId() == nutrientId).findFirst().get();
	}
}
