package com.justweighit;

import com.justweighit.resources.SearchResource;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class Application extends io.dropwizard.Application<Configuration> {
	
	@Override
	public void initialize(Bootstrap<Configuration> bootstrap) {
		super.initialize(bootstrap);
	}
	
	@Override
	public void run(Configuration configuration, Environment environment) throws Exception {
		environment.jersey().register(new SearchResource());
	}
	
	public static void main(String[] args) throws Exception {
		new Application().run(args);
	}
}
