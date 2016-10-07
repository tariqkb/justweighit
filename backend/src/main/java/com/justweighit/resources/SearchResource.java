package com.justweighit.resources;

import com.justweighit.api.FoodSearchResponse;
import com.justweighit.api.SearchRequest;
import com.justweighit.api.SearchResponse;
import com.justweighit.ndb.NDBClient;
import com.justweighit.ndb.NDBSearchResponseWrapper;
import com.justweighit.util.SearchExtract;
import com.justweighit.util.SearchExtractor;
import com.justweighit.util.TokenizedNDBItem;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Path("/search")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {
	
	private final NDBClient ndb;
	
	public SearchResource(NDBClient ndb) {
		this.ndb = ndb;
	}
	
	@POST
	public SearchResponse search(SearchRequest request) {
		
		SearchExtract extract = new SearchExtractor().extract(request.getText());
		String itemDescription = extract.getItemDescription();
		
		NDBSearchResponseWrapper results = ndb.search(itemDescription);
		
		List<TokenizedNDBItem> tokenizedItems = results.getList().getItem().stream().map(TokenizedNDBItem::new).collect(Collectors.toList());
		
		tokenizedItems.sort(TokenizedNDBItem.comparator(itemDescription));
		
		return new SearchResponse(tokenizedItems.stream()
			.map(tokenizedItem -> new FoodSearchResponse(tokenizedItem.getItem().getNdbno(), tokenizedItem.getItem().getName())).collect(Collectors.toList()));
	}
	
}
