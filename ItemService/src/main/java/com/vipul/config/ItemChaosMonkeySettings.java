package com.vipul.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

import de.codecentric.spring.boot.chaos.monkey.configuration.AssaultProperties;
import de.codecentric.spring.boot.chaos.monkey.configuration.ChaosMonkeyProperties;
import de.codecentric.spring.boot.chaos.monkey.configuration.ChaosMonkeySettings;
import de.codecentric.spring.boot.chaos.monkey.configuration.WatcherProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@ConfigurationProperties
public class ItemChaosMonkeySettings extends ChaosMonkeySettings {

	public ItemChaosMonkeySettings() {
		super();
	}

	public ItemChaosMonkeySettings(ChaosMonkeyProperties chaosMonkeyProperties2, AssaultProperties assaultProperties2,
			WatcherProperties watcherProperties2) {
		this.chaosMonkeyProperties = chaosMonkeyProperties2;
		this.assaultProperties = assaultProperties2;
		this.watcherProperties = watcherProperties2;
	}

	@NotNull
	private ChaosMonkeyProperties chaosMonkeyProperties;
	@NotNull
	private AssaultProperties assaultProperties;
	@NotNull
	private WatcherProperties watcherProperties;

}