package com.justweighit

import com.justweighit.commands.ImportAll
import com.justweighit.db.JooqBundle
import com.justweighit.errors.DefaultExceptionMapper
import com.justweighit.resources.FoodResource
import com.justweighit.resources.SearchResource
import com.justweighit.search.NDBSearcher
import com.justweighit.search.units.UnitSearcher
import com.justweighit.util.NDBIndex
import io.dropwizard.db.PooledDataSourceFactory
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import org.glassfish.hk2.utilities.binding.AbstractBinder
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.util.logging.Logger

class Application : io.dropwizard.Application<Configuration?>() {

    private val logger = Logger.getLogger(Application::class.java.name)
    
    val jooq: JooqBundle<Configuration?>
    override fun initialize(bootstrap: Bootstrap<Configuration?>) {
        super.initialize(bootstrap)
        bootstrap.addBundle(jooq)
        bootstrap.addCommand(ImportAll())
    }

    override fun run(configuration: Configuration?, environment: Environment) {
        val index = NDBIndex(DSL.using(jooq.configuration))
        environment.lifecycle().manage(index)
        environment.jersey().register(object : AbstractBinder() {
            override fun configure() {
                bind(NDBSearcher(index.index, index.analyzer, index.payloadSimilarity)).to(NDBSearcher::class.java)
            }
        })
        environment.jersey().register(SearchResource::class.java)
        environment.jersey().register(FoodResource::class.java)
        environment.jersey().register(DefaultExceptionMapper::class.java)
    }

    companion object {
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            Application().run(*args)
        }
    }

    init {
        jooq = object : JooqBundle<Configuration?>() {
            override fun getDataSourceFactory(configuration: Configuration?): PooledDataSourceFactory {
                val dataSourceFactory = configuration!!.dataSourceFactory!!

                
                dataSourceFactory.user = System.getenv("DATABASE_USER") ?: dataSourceFactory.user
                dataSourceFactory.password = System.getenv("DATABASE_PASSWORD") ?: dataSourceFactory.password
                dataSourceFactory.url = System.getenv("DATABASE_URL") ?: dataSourceFactory.url
                
                logger.info("[Configuration] database user=${dataSourceFactory.user}")
                logger.info("[Configuration] database password=*******************")
                logger.info("[Configuration] database url=${dataSourceFactory.url}")
                
                return dataSourceFactory
            }

            override fun configure(configuration: org.jooq.Configuration) {
                configuration.set(SQLDialect.POSTGRES)
            }
            
        }
    }
}