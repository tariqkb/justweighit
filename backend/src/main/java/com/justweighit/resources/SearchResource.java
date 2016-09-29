package com.justweighit.resources;

import com.justweighit.api.SearchRequest;
import com.justweighit.api.SearchResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/search")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {
	
	@GET
	public SearchResponse search(SearchRequest request) {
		return new SearchResponse("125", "4.40");
	}
}
