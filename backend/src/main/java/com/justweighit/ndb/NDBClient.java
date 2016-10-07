package com.justweighit.ndb;

import org.glassfish.jersey.logging.LoggingFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NDBClient {
	
	public final static String NDB_URL = "http://api.nal.usda.gov/ndb";
	public final static String NDB_SEARCH = "search";
	public final static String NDB_REPORTS = "reports";
	
	private final Logger logger = Logger.getLogger(NDBClient.class.getName());
	private final String apiKey;
	private final WebTarget ndb;
	
	public NDBClient(Client client, String apiKey) {
		this.apiKey = apiKey;
		this.ndb = client.target(NDB_URL);
		this.ndb.register(new LoggingFeature(logger, Level.INFO, null, null));
	}
	
	public NDBSearchResponseWrapper search(String text) {
		Response response = ndb.path(NDB_SEARCH)
			.queryParam("api_key", apiKey)
			.queryParam("q", text)
			.queryParam("format", "json")
			.queryParam("max", "100")
			.queryParam("ds", "Standard Reference")
			.request().get();
		
		if (response.getStatus() != Status.OK.getStatusCode()) {
			throw new RuntimeException("Error making call to NDB");
		}
		
		return response.readEntity(NDBSearchResponseWrapper.class);
	}
	
	public void lookup(String ndbno) {
		
	}
}
