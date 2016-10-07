package com.justweighit;

import com.justweighit.ndb.NDBClient;
import com.justweighit.resources.SearchResource;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.http.client.HttpClient;

import javax.ws.rs.client.Client;

public class Application extends io.dropwizard.Application<Configuration> {
	
	@Override
	public void initialize(Bootstrap<Configuration> bootstrap) {
		super.initialize(bootstrap);
	}
	
	@Override
	public void run(Configuration configuration, Environment environment) throws Exception {
		final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration())
			.build(getName());
		
		NDBClient ndbClient = new NDBClient(client, configuration.getApiKey());
		
		environment.jersey().register(new SearchResource(ndbClient));
	}
	
	public static void main(String[] args) throws Exception {
		new Application().run(args);
	}
}
