package com.justweighit.search;

public class NDBSearcherResult {
	private String ndbno;
	private String description;
	
	public NDBSearcherResult(String ndbno, String description) {
		this.ndbno = ndbno;
		this.description = description;
	}
	
	public String getNdbno() {
		return ndbno;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String toString() {
		return "(" + ndbno + ") " + description;
	}
}
