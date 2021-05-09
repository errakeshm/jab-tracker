package com.tracker.cowin.batch.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.tracker.cowin.batch.constants.PropertyConstants;
import com.tracker.cowin.batch.dataobjects.JobDefinition;

@PropertySource(value="classpath:scheduler.properties")
@Component
public class OnlineSchedulerDelegator {
	@Autowired
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	
	@Autowired
	private OnlineScheduler onlineScheduler;

	@Autowired
	private Environment environment;

	public void delegateTask() {
		String allJobNames = this.environment.getProperty(PropertyConstants.JOBS_NAMES);
		String[] jobNameArray = allJobNames != null ? allJobNames.split(PropertyConstants.COMMA) : null;

		if(jobNameArray != null) {
			for(String jobName : jobNameArray) {
				if(Boolean.parseBoolean(this.environment.getProperty(jobName + PropertyConstants.JOB_ENABLED))) {
					String cronExpression = this.environment.getProperty(jobName + PropertyConstants.SCHEDULER_EXPRESSION);
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
