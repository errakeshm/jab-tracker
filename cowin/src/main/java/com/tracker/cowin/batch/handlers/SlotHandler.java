package com.tracker.cowin.batch.handlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.tracker.cowin.batch.configurations.EnvironmentProperties;
import com.tracker.cowin.batch.constants.PropertyConstants;
import com.tracker.cowin.batch.constants.RequestConstants;
import com.tracker.cowin.batch.dataobjects.Center;
import com.tracker.cowin.batch.dataobjects.CenterWrapper;
import com.tracker.cowin.batch.dataobjects.JobConfiguration;
import com.tracker.cowin.batch.service.SlotService;
import com.tracker.cowin.helpers.StringHelper;

@PropertySource(value="classpath:configurations.properties")
@Service
public class SlotHandler {
	@Autowired
	private SlotService slotService;
	
	@Autowired
	private EnvironmentProperties environment;
	
	public List<Center> getAllSlots(Integer id, String date) {
		JobConfiguration configuration = new JobConfiguration();
		configuration.setApi(this.environment.getEnvironment().getProperty(PropertyConstants.CALENDAR_BY_DISTRICTS));
		configuration.addParam(RequestConstants.DISTRICT_ID, id.toString());
		configuration.addParam(RequestConstants.DATE, date);
		CenterWrapper wrapper = this.slotService.getAllSlots(configuration);
		return wrapper.getCenters();
	}
	
	public List<Center> getAvailableSlots(Integer id, String date, String feeType, Integer ageLimit, String vaccineType, Integer dose) {
		JobConfiguration configuration = new JobConfiguration();
		configuration.setApi(this.environment.getEnvironment().getProperty(PropertyConstants.CALENDAR_BY_DISTRICTS));
		configuration.addParam(RequestConstants.DISTRICT_ID, id.toString());
		configuration.addParam(RequestConstants.DATE, date);
		configuration.addParam(RequestConstants.FEE_TYPE, feeType);
		configuration.addParam(RequestConstants.AGE_LIMIT, StringHelper.convertIntegerToString(ageLimit));
		configuration.addParam(RequestConstants.TYPE, vaccineType);
		configuration.addParam(RequestConstants.DOSE, StringHelper.convertIntegerToString(dose));
		return this.slotService.getAvailableSlots(configuration);
	}
}
