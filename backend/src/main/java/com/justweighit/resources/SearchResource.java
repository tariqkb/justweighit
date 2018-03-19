package com.justweighit.resources;

import com.justweighit.api.FoodSearchResponse;
import com.justweighit.api.SearchRequest;
import com.justweighit.api.SearchResponse;
import com.justweighit.search.AmountExtractor;
import com.justweighit.search.NDBSearcher;
import com.justweighit.search.SearchParameters;
import com.justweighit.search.SearchParser;
import com.justweighit.search.units.UnitSearcher;
import io.progix.dropwizard.jooq.JooqConfiguration;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.stream.Collectors;

@Path("/search")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {
	
	private final DSLContext context;
	private final SearchParser searchParser;
	
	public SearchResource(@JooqConfiguration Configuration config, @Context NDBSearcher searcher) {
		this.context = DSL.using(config);
		this.searchParser = new SearchParser(new UnitSearcher(), new AmountExtractor(), searcher);
	}
	
	@POST
	public SearchResponse search(SearchRequest request) {
		SearchParameters params = searchParser.parse(request.getText());
	
		return new SearchResponse(params.getNdbnos().stream()
			.map(ndbSearcherResult -> new FoodSearchResponse(ndbSearcherResult.getNdbno(), ndbSearcherResult.getDescription(), params.getUnit(), params.getAmount())).collect(Collectors.toList()));
	}
}
