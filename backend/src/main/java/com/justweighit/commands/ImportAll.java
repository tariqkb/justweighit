package com.justweighit.commands;

import com.justweighit.Application;
import com.justweighit.Configuration;
import com.justweighit.etl.FoodDescriptionImport;
import com.justweighit.etl.NutrientContentImport;
import com.justweighit.etl.NutrientImport;
import com.justweighit.etl.WeightImport;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import static com.justweighit.database.jooq.Tables.*;

public class ImportAll extends ConfiguredCommand<Configuration> {

    public ImportAll() {
        super("import-all", "Runs all NDB file importers");
    }

    @Override
    public void configure(Subparser subparser) {
        super.configure(subparser);
    }

    @Override
    protected void run(Bootstrap<Configuration> bootstrap, Namespace namespace, Configuration configuration) throws Exception {

        final Environment environment = new Environment(
        		bootstrap.getApplication().getName(),
				bootstrap.getObjectMapper(),
				bootstrap.getValidatorFactory(),
				bootstrap.getMetricRegistry(),
				bootstrap.getClassLoader(),
				bootstrap.getHealthCheckRegistry(),
				configuration
		);

        configuration.getMetricsFactory().configure(environment.lifecycle(), bootstrap.getMetricRegistry());
        configuration.getServerFactory().configure(environment);

        bootstrap.run(configuration, environment);

        DSL.using(((Application) bootstrap.getApplication()).getJooq().getConfiguration())
                .transaction(config -> {
                    DSLContext context = DSL.using(config);
                    context.delete(WEIGHT).execute();
                    context.delete(NUTRIENT_CONTENT).execute();
                    context.delete(NUTRIENT).execute();
                    context.delete(FOOD).execute();
                    new FoodDescriptionImport(context).run();
                    new WeightImport(context).run();
                    new NutrientImport(context).run();
                    new NutrientContentImport(context).run();
                });
    }

}
