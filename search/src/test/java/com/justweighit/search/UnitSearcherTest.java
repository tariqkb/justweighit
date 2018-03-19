package com.justweighit.search;

import com.justweighit.search.units.UnitSearcher;
import com.justweighit.search.units.UnitWithMatchedString;
import com.justweighit.units.Unit;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static com.justweighit.units.Unit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UnitSearcherTest {
	
	private UnitSearcher searcher = new UnitSearcher();
	
	@Test()
	@Ignore
	public void searchTerms() {
		expect("tbps", tablespoon);
		expect("tablepsoon", tablespoon);
		expect("table spoon", tablespoon);
		expect("tps", teaspoon);
		expect("teapoon", teaspoon);
		expect("tea spoon", teaspoon);
		expect("floz", flOz);
		expect("oz", ounces);
		expect("ounce", ounces);
		expect("fluid oz", flOz);
		expect("fl ounce", flOz);
		expect("cup", cup);
		expect("cpu", cup);
	}
	
	@Test()
	@Ignore
	public void longQueries() {
		expect("1/2 cup flour", cup);
		expect("1/2 cpu flour", cup);
		expect("1 / 2 tbsp granulated sugar", tablespoon);
		expect("1 / 2 tbps granulated sugar", tablespoon);
		expect("tbps 1 / 2  granulated sugar", tablespoon);
		expect("tbps2  granulated sugar", tablespoon);
		expect("1 / 2 table spoon granulated sugar", tablespoon);
		expect("1 / 2 fluid oz granulated sugar", flOz);
		expect("1 / 2 fl ounces granulated sugar", flOz);
		expect("5.2 floz granulated sugar", flOz);
		expect("flour, 50 cup", cup);
		expect("flour, 50 cusp", cup);
	}
	
	private void expect(String query, Unit expectedUnit) {
		List<UnitWithMatchedString> units = searcher.run(query);
		Unit unit = units.isEmpty() ? null : units.get(0).getUnit();
		
		assertNotNull("Couldn't find unit for \"" + query + "\"", unit);
		assertEquals("Unit didn't match for \"" + query + "\"", expectedUnit, unit);
	}
}
