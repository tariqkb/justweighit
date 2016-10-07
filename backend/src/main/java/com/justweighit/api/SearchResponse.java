package com.justweighit.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchResponse {
	
	@JsonProperty
	private List<FoodSearchResponse> foods;
	
	public SearchResponse(List<FoodSearchResponse> foods) {
		this.foods = foods;
	}
}
