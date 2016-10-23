package com.justweighit.etl.reader;

import com.justweighit.etl.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class NDBFileReader extends BufferedReader {
	
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	public static final String SEP = "^";
	public static final String QUOTES = "~";
	
	public NDBFileReader(@NotNull DataSource dataSource) throws IOException {
		super(new InputStreamReader(dataSource.getDataFile(), "ASCII"));
	}
	
	public NDBRow parseLine() throws IOException {
		
		String line = readLine();
		if (line == null) {return null;}
		
		try {
			String[] values = StringUtils.splitPreserveAllTokens(line, SEP);
			
			List<NDBElement> elements = new ArrayList<>();
			for (String value : values) {
				if (value != null) {
					NDBElement element;
					if (value.length() > 1 &&
						StringUtils.startsWith(value, QUOTES) &&
						StringUtils.endsWith(value, QUOTES)) {
						
						element = new NDBElement(value.substring(1, value.length() - 1));
					} else {
						double doubleValue = value.isEmpty() ? 0 : Double.parseDouble(value);
						element = new NDBElement(doubleValue);
					}
					
					elements.add(element);
				} else {
					throw new RuntimeException("Didn't expect null value in line: " + Arrays.toString(values) + " parsed from line: " + line);
				}
				
			}
			
			return new NDBRow(elements);
		} catch (Throwable t) {
			logger.severe("Failed to parse line: " + line);
			throw new RuntimeException(t);
		}
	}
	
}
