package com.tracker.cowin.batch.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationLogger {
	private Logger logger = null;
	private ApplicationLogger() {
		this.logger = LoggerFactory.getLogger("application.log");
	}
	private static class ApplicationLoggerHolder {
		private static final ApplicationLogger INSTANCE = new ApplicationLogger();
	}
	public static ApplicationLogger getInstance() {
		return ApplicationLoggerHolder.INSTANCE;
	}
	public Logger getLogger() {
		return this.logger;
	}
}