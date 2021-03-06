package com.tracker.cowin.batch.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.tracker.cowin.batch.configurations.EnvironmentProperties;
import com.tracker.cowin.batch.constants.PropertyConstants;
import com.tracker.cowin.batch.dataobjects.JobDefinition;

@Component
public class OnlineSchedulerDelegator {
	@Autowired
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	
	@Autowired
	private OnlineScheduler onlineScheduler;

	@Autowired
	private EnvironmentProperties environment;

	public void delegateTask() {
		String allJobNames = this.environment.getEnvironment().getProperty(PropertyConstants.JOBS_NAMES);
		String[] jobNameArray = allJobNames != null ? allJobNames.split(PropertyConstants.COMMA) : null;

		if(jobNameArray != null) {
			for(String jobName : jobNameArray) {
				if(Boolean.parseBoolean(this.environment.getEnvironment().getProperty(jobName + PropertyConstants.JOB_ENABLED))) {
					String cronExpression = this.environment.getEnvironment().getProperty(jobName + PropertyConstants.SCHEDULER_EXPRESSION);
					JobDefinition jobDefinition = new JobDefinition();
					jobDefinition.setName(jobName);
					jobDefinition.setCronExpression(cronExpression);
					jobDefinition.setRunning(true);
					this.threadPoolTaskScheduler.schedule(this.onlineScheduler.createApplicationTask(jobDefinition), 
							new CronTrigger(jobDefinition.getCronExpression()));
				}
			}
		}
	}

}
