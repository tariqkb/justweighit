package com.justweighit;

import com.justweighit.commands.ImportAll;
import com.justweighit.db.JooqBundle;
import com.justweighit.errors.DefaultExceptionMapper;
import com.justweighit.resources.FoodResource;
import com.justweighit.resources.SearchResource;
import com.justweighit.search.NDBSearcher;
import com.justweighit.util.NDBIndex;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class Application extends io.dropwizard.Application<Configuration> {
	
	private final JooqBundle<Configuration> jooq;
	
	public Application() {
		jooq = new JooqBundle<>() {
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
	public void run(Configuration configuration, Environment environment) {
		NDBIndex index = new NDBIndex(DSL.using(jooq.getConfiguration()));
		environment.lifecycle().manage(index);
		
		environment.jersey().register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(new NDBSearcher(index.getIndex(), index.getAnalyzer(), index.getPayloadSimilarity())).to(NDBSearcher.class);
			}
		});

		environment.jersey().register(SearchResource.class);
		environment.jersey().register(FoodResource.class);
		environment.jersey().register(DefaultExceptionMapper.class);
	}
	
	public JooqBundle<Configuration> getJooq() {
		return jooq;
	}
	
	public static void main(String[] args) throws Exception {
		new Application().run(args);
	}
}
