package com.justweighit.search;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AmountExtractorTest {
	
	private AmountExtractor extractor = new AmountExtractor();
	
	@Test
	@Ignore
	public void testShort() {
		expect("1/2", 0.5);
		expect("1 / 2", 0.5);
		expect("0.5", 0.5);
		expect("00.5", 0.5);
		expect("00.50", 0.5);
		expect("5", 5.0);
	}
	
	@Test
	@Ignore
	public void testLong() {
		expect("1/2 cup flour", 0.5);
		expect("1 / 2 cup flour", 0.5);
		expect("0.5 and one cup flour", 0.5);
		expect("00.5, tabslep soon butter", 0.5);
		expect("00.50 ?", 0.5);
		expect("5 cu", 5.0);
	}
	
	private void expect(String query, Double value) {
//		Double actual = extractor.extract(query);
//		
//		assertEquals("Values don't match", actual, value);
	}
}
