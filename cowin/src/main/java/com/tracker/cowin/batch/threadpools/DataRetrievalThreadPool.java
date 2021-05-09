package com.tracker.cowin.batch.threadpools;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.tracker.cowin.batch.constants.CommonConstants;
import com.tracker.cowin.batch.dataobjects.CenterWrapper;

@Component
public class DataRetrievalThreadPool {
	private ExecutorService executor = null;
	private AtomicBoolean isActive = new AtomicBoolean();
	
	@PostConstruct
	public void initialize() {
		CommonConstants.LOGGER.debug("DataRetrievalThreadPool initialize called !!!!");
		destroy();
		executor =  Executors.newFixedThreadPool(5);
		isActive.set(false);
	}
	
	public CenterWrapper submitTask(DataRetrievalTask task) throws InterruptedException, ExecutionException {
		isActive.set(true);
		Future<CenterWrapper> centerWrapper = executor.submit(task);
		isActive.set(false);
		return centerWrapper.get();
	}
	
	public boolean isJobRunning() {
		return isActive.get();
	}
	
	@PreDestroy
	public void destroy() {
		CommonConstants.LOGGER.debug("DataRetrievalThreadPool destroy called  !!!!");
		try {
		if(executor != null && !executor.isTerminated()) {
			executor.shutdown();
			isActive.set(false);
			if(!executor.awaitTermination(60, TimeUnit.SECONDS)) {
				executor.shutdownNow();
			}
			executor = null;
		}
		} catch(InterruptedException exception) {
			CommonConstants.LOGGER.error(exception.getMessage());
			executor = null;
		}
	}
	
}
