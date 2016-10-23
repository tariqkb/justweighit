package com.justweighit.etl.reader;

public class NDBElement {
	private final String stringValue;
	private final Double doubleValue;
	
	public NDBElement(String stringValue) {
		if(stringValue == null) {
			throw new RuntimeException("String value cannot be null.");
		}
		
		this.stringValue = stringValue;
		this.doubleValue = null;
	}
	
	public NDBElement(double doubleValue) {
		this.stringValue = null;
		this.doubleValue = doubleValue;
	}
	
	public String getStringValue() {
		if(stringValue == null) {
			throw new RuntimeException("Trying to get string value for double NDBElement: " + this);
		}
		return stringValue;
	}
	
	public Double getDoubleValue() {
		if(doubleValue == null) {
			throw new RuntimeException("Trying to get double value for string NDBElement: " + this);
		}
		return doubleValue;
	}
	
	public boolean isString() {
		return stringValue != null;
	}
	
	public boolean isDouble() {
		return doubleValue != null;
	}
	
	@Override
	public String toString() {
		if(isString()) {
			return "STRING[" + getStringValue() + "]";
		} else if(isDouble()) {
			return "DOUBLE[" + getDoubleValue() + "]";
		}
		throw new RuntimeException();
	}
}
