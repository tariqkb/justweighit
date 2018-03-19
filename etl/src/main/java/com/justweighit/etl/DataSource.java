package com.justweighit.etl;

import java.io.InputStream;

public enum DataSource {
	
	FOOD_DES, WEIGHT, NUT_DATA, NUTR_DEF;
	
	public static final String STANDARD_REFERENCE_VERSION = "sr28asc";
	
	public InputStream getDataFile() {
		String path = STANDARD_REFERENCE_VERSION + "/" + name() + ".txt";
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream(path);
		if(stream == null) {
			throw new RuntimeException("Can't find path: " + path);
		}
		return stream;
	}
}
