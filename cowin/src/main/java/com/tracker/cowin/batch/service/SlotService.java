package com.tracker.cowin.batch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import com.tracker.cowin.batch.dataobjects.JobConfiguration;
import com.tracker.cowin.batch.dataobjects.Session;
import com.tracker.cowin.batch.utility.SlotFilterPredicates;
import com.tracker.cowin.helpers.StringHelper;

@Service
public class SlotService {
	@Value("${appointments.calendarByDistrict.protected}")
	private String urlAppointmentCalendarByDistrict;
	
	@Autowired
	private RestTemplate restTemplate;

	public CenterWrapper getAllSlots(JobConfiguration configuration){
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(configuration.getApi());
		for(Entry<String,String> entry : configuration.getParams().entrySet()) {
			builder.queryParam(entry.getKey(),entry.getValue());
		}
		
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
	
	public List<Center> getAvailableSlots(JobConfiguration configuration){
		CenterWrapper centerWrapper = this.getAllSlots(configuration);
		List<Center> availableCenters = new ArrayList<>();
		Map<String,String> params = configuration.getParams();
		if(centerWrapper != null && centerWrapper.getCenters() != null) {
			for(Center center : centerWrapper.getCenters()) {
				Center resultCenter = center;
				if(StringHelper.ifNullOrMatch(center.getFeeType(), params.get(RequestConstants.FEE_TYPE))) {
					VaccineType vaccine = getVaccineType(params.get(RequestConstants.TYPE));
					List<Session> availableSessions = findAvailableSessions(center, params.get(RequestConstants.AGE_LIMIT), vaccine);
					if(availableSessions != null && !availableSessions.isEmpty()) {
						resultCenter.setSessions(availableSessions);
						availableCenters.add(resultCenter);
					}
				}
			}
		}
		return availableCenters;
	}
	
	public List<Session> findAvailableSessions(Center center, String ageLimit, VaccineType type){
		return center.getSessions().stream()
				.filter(SlotFilterPredicates.findSessionByAvailableCapacity())
				.filter(SlotFilterPredicates.findSessionByAgeLimit(ageLimit != null ? Integer.parseInt(ageLimit):null))
				.filter(SlotFilterPredicates.findSessionByVaccineType(type))
				.collect(Collectors.toList());
	}
	
	private VaccineType getVaccineType(String type) {
		return (type != null ) ? VaccineType.valueOf(type) : null; 
	}
}
