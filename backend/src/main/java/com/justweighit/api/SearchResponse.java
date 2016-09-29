package com.justweighit.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchResponse {
	
	@JsonProperty
	private String ounces;
	@JsonProperty
	private String grams;
	
	public SearchResponse(String grams, String ounces) {
		this.grams = grams;
		this.ounces = ounces;
	}
	
	public String getGrams() {
		return grams;
	}
	
	public String getOunces() {
		return ounces;
	}
}
