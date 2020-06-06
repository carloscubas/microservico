package com.example.ecommerce.configuration;

import org.springframework.context.annotation.Configuration;

import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

@Configuration
public class PrometheusConf {
	
	PrometheusMeterRegistry prometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

}
