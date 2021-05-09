package com.tracker.cowin.batch.parser;

import com.tracker.cowin.batch.dataobjects.CenterWrapper;
import com.tracker.cowin.batch.dataobjects.JobConfiguration;
import com.tracker.cowin.batch.service.SlotService;

interface JobElementI {
	public CenterWrapper accept(CowinVisitorI visitor);
}

public class JobElement implements JobElementI{
	private JobConfiguration configuration;
	private SlotService slotService;
	public JobElement(JobConfiguration configuration, SlotService slotService) {
		this.configuration = configuration;
		this.slotService = slotService;
	}
	public JobConfiguration getConfiguration() {
		return configuration;
	}
	public void setConfiguration(JobConfiguration configuration) {
		this.configuration = configuration;
	}
	public void setSlotService(SlotService slotService) {
		this.slotService = slotService;
	}
	public SlotService getSlotService() {
		return slotService;
	}
	@Override
	public CenterWrapper accept(CowinVisitorI visitor) {
		return visitor.visit(this);
	}
	
}
