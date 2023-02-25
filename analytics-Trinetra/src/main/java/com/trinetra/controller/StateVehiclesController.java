package com.trinetra.controller;
import static com.trinetra.model.enums.Status.FAILED;
import static com.trinetra.model.enums.Status.SUCCESS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trinetra.model.wrapper.ResponseWrapper;
import com.trinetra.service.StateVehiclesService;

@RestController
@RequestMapping("/api/v1/stateVehicle")
public class StateVehiclesController {

	@Autowired
	private StateVehiclesService stateVehiclesService;
	
	@GetMapping("/getMapDetailsByDistrict")
	public ResponseEntity<ResponseWrapper> getMapDetailsByDistrict(@RequestParam("district") String district){
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, stateVehiclesService.getMapDetailsByDistrict(district)),HttpStatus.OK);
	}
	
	@GetMapping("/getMapDetailsByStateName")
	public ResponseEntity<ResponseWrapper> getMapDetailsByStateName(@RequestParam("stateName") String stateName){
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, stateVehiclesService.getMapDetailsByStateName(stateName)),HttpStatus.OK);
	}
}
