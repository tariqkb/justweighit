package com.justweighit.etl.reader;

import java.util.List;

public class NDBRow {
	private List<NDBElement> elements;
	
	public NDBRow(List<NDBElement> elements) {
		this.elements = elements;
	}
	
	public NDBElement get(NDBColumn column) {
		return elements.get(column.index());
	}
	
	@Override
	public String toString() {
		return "NDBRow{" +
			"elements=" + elements +
			'}';
	}
}
