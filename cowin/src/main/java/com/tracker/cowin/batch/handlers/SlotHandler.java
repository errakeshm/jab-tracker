package com.tracker.cowin.batch.handlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tracker.cowin.batch.dataobjects.Center;
import com.tracker.cowin.batch.dataobjects.CenterWrapper;
import com.tracker.cowin.batch.service.SlotService;

@Service
public class SlotHandler {
	@Autowired
	private SlotService slotService;
	
	public List<Center> getAllSlots(int id, String date) {
		CenterWrapper wrapper = this.slotService.getAllSlots(id, date);
		return wrapper.getCenters();
	}
	
	public List<Center> getAvailableSlots(Integer id, String date, String feeType, Integer ageLimit, String vaccineType) {
		return this.slotService.getAvailableSlots(id, date, feeType, ageLimit, vaccineType);
	}
}
