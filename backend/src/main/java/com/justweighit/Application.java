package com.justweighit;

import com.justweighit.commands.ImportAll;
import com.justweighit.ndb.NDBClient;
import com.justweighit.resources.SearchResource;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.progix.dropwizard.jooq.JooqBundle;
import org.apache.http.client.HttpClient;
import org.jooq.SQLDialect;

import javax.ws.rs.client.Client;
import java.sql.Connection;

public class Application extends io.dropwizard.Application<Configuration> {
	
	private final JooqBundle<Configuration> jooq;
	
	public Application() {
		jooq = new JooqBundle<Configuration>() {
			@Override
			public PooledDataSourceFactory getDataSourceFactory(Configuration configuration) {
				return configuration.getDataSourceFactory();
			}
			
			@Override
			protected void configure(org.jooq.Configuration configuration) {
				configuration.set(SQLDialect.POSTGRES);
			}
		};
	}
	
	@Override
	public void initialize(Bootstrap<Configuration> bootstrap) {
		super.initialize(bootstrap);
		bootstrap.addBundle(jooq);
		
		bootstrap.addCommand(new ImportAll());
	}
	
	@Override
	public void run(Configuration configuration, Environment environment) throws Exception {
		final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration())
			.build(getName());
		
		NDBClient ndbClient = new NDBClient(client, configuration.getApiKey());
	
		environment.jersey().register(new SearchResource(ndbClient));
	}
	
	public JooqBundle<Configuration> getJooq() {
		return jooq;
	}
	
	public static void main(String[] args) throws Exception {
		new Application().run(args);
	}
}
