package com.justweighit

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Configuration
import io.dropwizard.client.JerseyClientConfiguration
import io.dropwizard.db.DataSourceFactory
import javax.validation.Valid
import javax.validation.constraints.NotNull

class Configuration : Configuration() {
    @JsonProperty("apiKey")
    val apiKey: String? = null

    @JsonProperty("jerseyClient")
    val jerseyClientConfiguration: @Valid @NotNull JerseyClientConfiguration? = JerseyClientConfiguration()

    @JsonProperty("database")
    val dataSourceFactory: @Valid @NotNull DataSourceFactory? = DataSourceFactory()
}