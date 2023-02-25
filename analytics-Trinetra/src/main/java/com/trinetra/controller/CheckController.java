package com.trinetra.controller;

import static com.trinetra.model.enums.Status.SUCCESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trinetra.model.page.response.RenewalDeviceDetailsResponse;
import com.trinetra.model.response.DeviceMasterModelResponse;
import com.trinetra.model.wrapper.ResponseWrapper;
import com.trinetra.service.CheckService;

@RestController
@RequestMapping("/api/v1/check")
public class CheckController {

	@Autowired
	private	CheckService checkService;
	
	@GetMapping("/vehicleActivation")
	public ResponseEntity<ResponseWrapper> getVehicleActivationList(@RequestParam("page") int page, @RequestParam("size") int size) {
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, checkService.getAllDeviceData(page, size)), HttpStatus.OK);

	}

	@GetMapping("/searchVehicleActivation")
	public ResponseEntity<ResponseWrapper> getVehicleActivationSearch(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("searchBy") String searchBy) {
		Page<DeviceMasterModelResponse> vehicleActResponse = checkService.getAllSearchDeviceData(page, size,searchBy);
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, vehicleActResponse), HttpStatus.OK);
	}
	
	@GetMapping("/renewalDevicesDetails")
	public ResponseEntity<ResponseWrapper>getRenewalDevicesDetails(@RequestParam("page") int page,
			@RequestParam("size") int size) {
		Page<RenewalDeviceDetailsResponse> renewalDeviceDetailsResponse = checkService.getRenewalDevicesDetails(page, size);
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, renewalDeviceDetailsResponse), HttpStatus.OK);
	}
	
	@GetMapping("/searchRenewalDevicesDetails")
	public ResponseEntity<ResponseWrapper> getSearchRenewalDevicesDetails(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam("searchBy") String searchBy){
		Page<RenewalDeviceDetailsResponse> renewalDeviceDetailsResponse = checkService.getSearchRenewalDevicesDetails(page, size, searchBy);
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, renewalDeviceDetailsResponse), HttpStatus.OK);
	}
}
