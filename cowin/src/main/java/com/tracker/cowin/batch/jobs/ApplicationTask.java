package com.tracker.cowin.batch.jobs;

import com.tracker.cowin.batch.dataobjects.JobDefinition;

public class ApplicationTask implements Runnable{
	
	private JobDefinition jobDefinition;
	
	public ApplicationTask(JobDefinition jobDefinition) {
		this.jobDefinition = jobDefinition;
	}
	@Override
	public void run() {
		
	}
}
