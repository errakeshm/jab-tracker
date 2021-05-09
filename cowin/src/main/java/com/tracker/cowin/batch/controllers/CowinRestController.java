package com.tracker.cowin.batch.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.cowin.batch.dataobjects.Center;
import com.tracker.cowin.batch.handlers.SlotHandler;

@RestController
public class CowinRestController {
	
	@Autowired
	private SlotHandler slotHandler;
	
	@GetMapping(value ="/all")
	public ResponseEntity<List<Center>> getAll(@RequestParam(name ="district_id") Integer id, @RequestParam String date){
		List<Center> centers = this.slotHandler.getAllSlots(id, date);
		return new ResponseEntity<>(centers, HttpStatus.OK);
	}
	
	@GetMapping(value ="/available")
	public ResponseEntity<List<Center>> getAvailableSlots(@RequestParam(name="district_id") Integer id, @RequestParam String date, @RequestParam(required=false, name="fee_type") String feeType, @RequestParam(required=false, name="age_limit") Integer ageLimit, @RequestParam(required=false, name="type") String vaccineType){
		List<Center> centers = this.slotHandler.getAvailableSlots(id, date, feeType, ageLimit, vaccineType);
		return new ResponseEntity<>(centers, HttpStatus.OK);
	}
}
