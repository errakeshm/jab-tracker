package com.tracker.cowin.batch.jobs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class JabTrackerListener implements ApplicationListener<ApplicationReadyEvent>{
	
	@Autowired
	private OnlineSchedulerDelegator onlineSchedulerDelegator;
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		this.onlineSchedulerDelegator.delegateTask();
	}
}