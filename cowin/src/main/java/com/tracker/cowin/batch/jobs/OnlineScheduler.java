package com.tracker.cowin.batch.jobs;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tracker.cowin.batch.dataobjects.JobDefinition;

@Component
public class OnlineScheduler {
	
	@Autowired
	private JabTrackerLauncher jabTrackerLauncher;
	
	private Map<String, ApplicationTask> jobMap = new ConcurrentHashMap<>();
	
	public ApplicationTask createApplicationTask(JobDefinition jobDefinition) {
		if (this.jobMap.get(jobDefinition.getName()) == null)
			this.jobMap.put(jobDefinition.getName(), 
					new ApplicationTask(jobDefinition, jabTrackerLauncher));
		return this.jobMap.get(jobDefinition.getName());
	}
}
