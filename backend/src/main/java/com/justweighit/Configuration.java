package com.justweighit;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class Configuration extends io.dropwizard.Configuration {
	
	
	@JsonProperty("apiKey")
	private String apiKey;
	
	@Valid
	@NotNull
	@JsonProperty("jerseyClient")
	private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();
	
	public JerseyClientConfiguration getJerseyClientConfiguration() {
		return jerseyClient;
	}
	
	public String getApiKey() {
		return apiKey;
	}
}
