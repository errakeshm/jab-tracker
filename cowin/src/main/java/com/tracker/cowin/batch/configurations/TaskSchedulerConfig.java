package com.tracker.cowin.batch.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class TaskSchedulerConfig {
	
	@Value("${taskScheduler.poolSize}")
	private Integer poolSize;
	
	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(poolSize);
		taskScheduler.setThreadNamePrefix("threadPoolTaskScheduler");
		return taskScheduler;
	}	
}
