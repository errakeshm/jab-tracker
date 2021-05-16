package com.tracker.cowin.batch.utility;

import java.util.function.Predicate;

import com.tracker.cowin.batch.constants.ApplicationConstants.VaccineType;
import com.tracker.cowin.batch.dataobjects.Center;
import com.tracker.cowin.batch.dataobjects.Session;

public class SlotFilterPredicates {
	private SlotFilterPredicates() {}
	public static Predicate<Center> findCenterByFeeType(String feeType) {
		return (center)->((feeType != null) ? center.getFeeType().equalsIgnoreCase(feeType) : true);
	}
	
	public static Predicate<Session> findSessionByAgeLimit(Integer ageLimit) {
		return (sess)->((ageLimit != null) ? sess.getAgeLimit() == ageLimit : true);
	}
	
	public static Predicate<Session> findSessionByAvailableCapacity(Integer dose) {
		
		if(dose != null) {
			return (sess)->(sess.getAvailableCapacity() > 0
					&& ((dose == 1 && sess.getAvailableCapacityDose1() > 0)|| (dose ==2 && sess.getAvailableCapacityDose2() > 0) || (dose == 12 && (sess.getAvailableCapacityDose1() > 0 || sess.getAvailableCapacityDose2() > 0))));  
		} else {
			return (sess)->(sess.getAvailableCapacity() > 0);
		}
	}
	
	public static Predicate<Session> findSessionByVaccineType(VaccineType type) {
		return (session)->((type != null) ? session.getVaccine().equalsIgnoreCase(type.getVaccineStr()) : true);
	}
	
}
