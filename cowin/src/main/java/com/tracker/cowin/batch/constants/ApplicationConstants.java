package com.tracker.cowin.batch.constants;

public class ApplicationConstants {
	private ApplicationConstants() {}
	public enum VaccineType{
		COVAXIN("COVAXIN"),
		COVISHIELD("COVISHIELD");
		
		private String type;
		private VaccineType(String type) {
			this.type = type;
		}
		public String getVaccineStr() {
			return this.type;
		}
	};
}
