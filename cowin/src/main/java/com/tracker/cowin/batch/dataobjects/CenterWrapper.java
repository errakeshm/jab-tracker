package com.tracker.cowin.batch.dataobjects;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CenterWrapper {
	private List<Center> centers;
	public List<Center> getCenters() {
		return centers;
	}
	public void setCenters(List<Center> centers) {
		this.centers = centers;
	}
}
