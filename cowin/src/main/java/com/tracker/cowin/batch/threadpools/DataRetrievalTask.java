package com.tracker.cowin.batch.threadpools;


import java.util.concurrent.Callable;

import com.tracker.cowin.batch.dataobjects.CenterWrapper;
import com.tracker.cowin.batch.parser.CowinVisitor;
import com.tracker.cowin.batch.parser.CowinVisitorI;
import com.tracker.cowin.batch.parser.JobElement;


public class DataRetrievalTask implements Callable<CenterWrapper>{

	private JobElement job;
	
	public DataRetrievalTask(JobElement job) {
		this.job = job;
	}
	
	@Override
	public CenterWrapper call() throws Exception {
		CowinVisitorI cowinVisitor = new CowinVisitor();
		return job.accept(cowinVisitor);
	}
}