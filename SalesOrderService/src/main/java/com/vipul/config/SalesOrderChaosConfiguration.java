package com.vipul.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import de.codecentric.spring.boot.chaos.monkey.component.ChaosMonkey;
import de.codecentric.spring.boot.chaos.monkey.conditions.AttackComponentCondition;
import de.codecentric.spring.boot.chaos.monkey.conditions.AttackControllerCondition;
import de.codecentric.spring.boot.chaos.monkey.conditions.AttackRestControllerCondition;
import de.codecentric.spring.boot.chaos.monkey.conditions.AttackServiceCondition;
import de.codecentric.spring.boot.chaos.monkey.configuration.AssaultProperties;
import de.codecentric.spring.boot.chaos.monkey.configuration.ChaosMonkeyProperties;
import de.codecentric.spring.boot.chaos.monkey.configuration.ChaosMonkeySettings;
import de.codecentric.spring.boot.chaos.monkey.configuration.WatcherProperties;
import de.codecentric.spring.boot.chaos.monkey.endpoints.ChaosMonkeyJmxEndpoint;
import de.codecentric.spring.boot.chaos.monkey.endpoints.ChaosMonkeyRestEndpoint;
import de.codecentric.spring.boot.chaos.monkey.watcher.SpringComponentAspect;
import de.codecentric.spring.boot.chaos.monkey.watcher.SpringControllerAspect;
import de.codecentric.spring.boot.chaos.monkey.watcher.SpringRestControllerAspect;
import de.codecentric.spring.boot.chaos.monkey.watcher.SpringServiceAspect;

@Configuration
@Profile("chaos-monkey")
@EnableConfigurationProperties({ SalesOrderChaosMonkeySettings.class, AssaultProperties.class, WatcherProperties.class })
public class SalesOrderChaosConfiguration {
	private static final Logger LOGGER = LoggerFactory.getLogger(ChaosMonkey.class);
	private final ChaosMonkeyProperties chaosMonkeyProperties;
	private final WatcherProperties watcherProperties;
	private final AssaultProperties assaultProperties;

	public SalesOrderChaosConfiguration(ChaosMonkeyProperties chaosMonkeyProperties, WatcherProperties watcherProperties,
			AssaultProperties assaultProperties) {
		this.chaosMonkeyProperties = chaosMonkeyProperties;
		this.watcherProperties = watcherProperties;
		this.assaultProperties = assaultProperties;
		LOGGER.info("Chaos Monkey - Sales Order Service");
	}

	@Primary
	@Bean
	public ChaosMonkeySettings settings() {
		return new SalesOrderChaosMonkeySettings(chaosMonkeyProperties, assaultProperties, watcherProperties);
	}

	@Primary
	@Bean
	public ChaosMonkey chaosMonkey() {
		return new ChaosMonkey(settings());
	}

	
	@Primary
	@Bean
	@Conditional(AttackControllerCondition.class)
	public SpringControllerAspect controllerAspect() {
		return new SpringControllerAspect(chaosMonkey());
	}

	@Primary
	@Bean
	@Conditional(AttackRestControllerCondition.class)
	public SpringRestControllerAspect restControllerAspect() {
		return new SpringRestControllerAspect(chaosMonkey());
	}

	@Primary
	@Bean
	@Conditional(AttackServiceCondition.class)
	public SpringServiceAspect serviceAspect() {
		return new SpringServiceAspect(chaosMonkey());
	}

	@Primary
	@Bean
	@Conditional(AttackComponentCondition.class)
	public SpringComponentAspect componentAspect() {
		return new SpringComponentAspect(chaosMonkey());
	}

	@Primary
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnEnabledEndpoint
	public ChaosMonkeyRestEndpoint chaosMonkeyRestEndpoint() {
		return new ChaosMonkeyRestEndpoint(settings());
	}

	@Primary
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnEnabledEndpoint
	public ChaosMonkeyJmxEndpoint chaosMonkeyJmxEndpoint() {
		return new ChaosMonkeyJmxEndpoint(settings());
	}

}
