package com.justweighit.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchRequest {

	@JsonProperty
	private String text;
	
	public String getText() {
		return text;
	}
}