package com.tracker.cowin.batch.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@PropertySources({
	@PropertySource("classpath:configurations.properties"),
	@PropertySource("classpath:scheduler.properties")
})
@Component
public class EnvironmentProperties {
	@Autowired
	private Environment environment;
	
	public Environment getEnvironment() {
		return this.environment;
	}
}
