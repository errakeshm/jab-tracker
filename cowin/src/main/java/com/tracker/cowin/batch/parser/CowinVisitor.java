package com.tracker.cowin.batch.parser;

import java.util.List;

import com.tracker.cowin.batch.dataobjects.Center;
import com.tracker.cowin.batch.dataobjects.CenterWrapper;

public class CowinVisitor implements CowinVisitorI{
	
	@Override
	public CenterWrapper visit(JobElement jobElement) {
		CenterWrapper centerWrapper = null;
		List<Center> centerList = jobElement.getSlotService().getAvailableSlots(jobElement.getConfiguration());
		if(centerList != null && !centerList.isEmpty()) {
			centerWrapper = new CenterWrapper();
			centerWrapper.setCenters(centerList);
		}
		return centerWrapper;
	}
}
