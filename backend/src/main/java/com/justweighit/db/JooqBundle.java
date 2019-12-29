package com.justweighit.db;

import io.dropwizard.ConfiguredBundle;
import io.dropwizard.db.DatabaseConfiguration;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.jooq.Configuration;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;

public abstract class JooqBundle<T extends io.dropwizard.Configuration> implements ConfiguredBundle<T>,
        DatabaseConfiguration<T> {

    private Configuration configuration;

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }

    @Override
    public void run(T dwConfiguration, Environment environment) throws Exception {
        final PooledDataSourceFactory dbConfig = getDataSourceFactory(dwConfiguration);
        ManagedDataSource dataSource = dbConfig.build(environment.metrics(), "jooq");

        configuration = new DefaultConfiguration().set(new DataSourceConnectionProvider(dataSource));
        ;
        configure(configuration);

        environment.jersey().register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(configuration).to(Configuration.class);
            }
        });

        environment.lifecycle().manage(dataSource);

        environment.healthChecks().register("jooq", new JooqHealthCheck(
                DSL.using(configuration),
                dbConfig.getValidationQuery().orElse("SELECT 1"))
        );
    }

    public Configuration getConfiguration() {
        return configuration.derive();
    }

    protected abstract void configure(Configuration configuration);

}
