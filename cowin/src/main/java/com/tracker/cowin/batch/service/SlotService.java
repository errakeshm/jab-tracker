package com.tracker.cowin.batch.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.tracker.cowin.batch.constants.RequestConstants;
import com.tracker.cowin.batch.constants.ApplicationConstants.VaccineType;
import com.tracker.cowin.batch.dataobjects.Center;
import com.tracker.cowin.batch.dataobjects.CenterWrapper;
import com.tracker.cowin.batch.dataobjects.Session;
import com.tracker.cowin.batch.utility.SlotFilterPredicates;
import com.tracker.cowin.helpers.StringHelper;

@Service
public class SlotService {
	@Value("${appointments.calendarByDistrict.protected}")
	private String urlAppointmentCalendarByDistrict;
	
	@Value("${test}")
	private String testUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public CenterWrapper getAllSlots(Integer id, String date){
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlAppointmentCalendarByDistrict);
		builder.queryParam(RequestConstants.DISTRICT_ID, id);
		builder.queryParam(RequestConstants.DATE, date);
		
		HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);
		HttpEntity<CenterWrapper> response = null;
		try {
			response = this.restTemplate.exchange(builder.toUriString(), 
					HttpMethod.GET, 
					entity,
					CenterWrapper.class);
		} catch(HttpClientErrorException ex) {
			ex.printStackTrace();
		}
		
		return (response != null ) ? response.getBody() : null;
	}
	
	public List<Center> getAvailableSlots(Integer id, String date, String feeType, Integer ageLimit, String vaccineType){
		CenterWrapper centerWrapper = this.getAllSlots(id, date);
		List<Center> availableCenters = new ArrayList<>();
		if(centerWrapper != null && centerWrapper.getCenters() != null) {
			for(Center center : centerWrapper.getCenters()) {
				Center resultCenter = center;
				if(StringHelper.ifNullOrMatch(center.getFeeType(), feeType)) {
					VaccineType vaccine = getVaccineType(vaccineType);
					List<Session> availableSessions = findAvailableSessions(center, ageLimit, vaccine);
					if(availableSessions != null && !availableSessions.isEmpty()) {
						resultCenter.setSessions(availableSessions);
						availableCenters.add(resultCenter);
					}
				}
			}
		}
		return availableCenters;
	}
	
	public List<Session> findAvailableSessions(Center center, Integer ageLimit, VaccineType type){
		return center.getSessions().stream()
				.filter(SlotFilterPredicates.findSessionByAvailableCapacity())
				.filter(SlotFilterPredicates.findSessionByAgeLimit(ageLimit))
				.filter(SlotFilterPredicates.findSessionByVaccineType(type))
				.collect(Collectors.toList());
	}
	
	private VaccineType getVaccineType(String type) {
		return (type != null ) ? VaccineType.valueOf(type) : null; 
	}
}
