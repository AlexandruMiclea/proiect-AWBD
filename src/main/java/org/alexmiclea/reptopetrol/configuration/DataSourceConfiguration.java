package org.alexmiclea.reptopetrol.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = {"org.alexmiclea.reptopetrol.repository.monitoring", "org.alexmiclea.reptopetrol.repository.payment"})
@EnableJpaRepositories(basePackages = {"org.alexmiclea.reptopetrol.repository.management", "org.alexmiclea.reptopetrol.repository.user"})
@Configuration
public class DataSourceConfiguration { }
