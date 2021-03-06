package com.tracker.cowin.batch.dataobjects;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Session {
	@JsonProperty("session_id")
	private String sessionId;
	private String date;
	@JsonProperty("min_age_limit")
	private int ageLimit;
	@JsonProperty("available_capacity")
	private int availableCapacity;
	@JsonProperty("available_capacity_dose1")
	private int availableCapacityDose1;
	@JsonProperty("available_capacity_dose2")
	private int availableCapacityDose2;
	private String vaccine;
	private List<String> slots;
	public int getAvailableCapacityDose1() {
		return availableCapacityDose1;
	}
	public void setAvailableCapacityDose1(int availableCapacityDose1) {
		this.availableCapacityDose1 = availableCapacityDose1;
	}
	public int getAvailableCapacityDose2() {
		return availableCapacityDose2;
	}
	public void setAvailableCapacityDose2(int availableCapacityDose2) {
		this.availableCapacityDose2 = availableCapacityDose2;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getAgeLimit() {
		return ageLimit;
	}
	public void setAgeLimit(int ageLimit) {
		this.ageLimit = ageLimit;
	}
	public String getVaccine() {
		return vaccine;
	}
	public void setVaccine(String vaccine) {
		this.vaccine = vaccine;
	}
	public List<String> getSlots() {
		return slots;
	}
	public void setSlots(List<String> slots) {
		this.slots = slots;
	}
	public void setAvailableCapacity(int availableCapacity) {
		this.availableCapacity = availableCapacity;
	}
	public int getAvailableCapacity() {
		return availableCapacity;
	}
	@Override
	public String toString() {
		return "Session [sessionId=" + sessionId + ", date=" + date + ", ageLimit=" + ageLimit + ", availableCapacity="
				+ availableCapacity + ", vaccine=" + vaccine + ", slots=" + slots + "]";
	}
	
}
