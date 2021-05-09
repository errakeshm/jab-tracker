package com.tracker.cowin.batch.jobs;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.tracker.cowin.batch.dataobjects.CenterWrapper;
import com.tracker.cowin.batch.dataobjects.JobConfiguration;
import com.tracker.cowin.batch.parser.JobElement;
import com.tracker.cowin.batch.service.SlotService;
import com.tracker.cowin.batch.threadpools.AudioTask;
import com.tracker.cowin.batch.threadpools.DataRetrievalTask;
import com.tracker.cowin.batch.threadpools.DataRetrievalThreadPool;
import com.tracker.cowin.util.SoundUtil;

@Component
public class JabTrackerLauncher {
	@Autowired
	private DataRetrievalThreadPool dataRetrievalThreadPool;
	@Autowired
	private ApplicationContext context;
	@Autowired
	private SlotService slotService;
	@Autowired
	private SoundUtil soundUtil;

	@Value("${alarm.enabled}")
	private boolean isAlarmEnabled;

	public CenterWrapper submit(String name) throws InterruptedException, ExecutionException {
		if(!this.dataRetrievalThreadPool.isJobRunning()) {
			JobConfiguration configuration = (JobConfiguration) this.context.getBean(name);
			JobElement element = new JobElement(configuration,slotService);
			return this.dataRetrievalThreadPool.submitTask(new DataRetrievalTask(element));
		}
		return null;
	}

	public void play() {
		if(isAlarmEnabled) {
			//this.soundUtil.play();
			this.dataRetrievalThreadPool.submitTask(new AudioTask(this.soundUtil));
		}
	}
}
