package com.trinetra.controller;

import static com.trinetra.model.enums.Status.SUCCESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trinetra.model.wrapper.ResponseWrapper;
import com.trinetra.service.DeviceHealthPacketService;

@RestController
@RequestMapping("/api/v1/devicehealth")
public class DeviceHealthPacketController {
	
	@Autowired
	private DeviceHealthPacketService deviceHealthPacketService;
	
	@GetMapping("/aisHealthCheck")
	public ResponseEntity<ResponseWrapper> getDevicHealthCheckeDetails(@RequestParam("page") int page, @RequestParam("size") int size )
	{	
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, deviceHealthPacketService.getDevicHealthCheckeDetails(page, size)),
				HttpStatus.OK);
	}
	
	@GetMapping("/aisHealthKnowledgeCheck")
	public ResponseEntity<ResponseWrapper> getDevicHealthKnowledgeCheckDetails(@RequestParam("page") int page, @RequestParam("size") int size )
	{	
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, deviceHealthPacketService.getDeviceHealthKnowledgeCheckDetails(page, size)),
				HttpStatus.OK);
	}
	
	@GetMapping("/aisHealthKnowledgeCheckSearch")
	public ResponseEntity<ResponseWrapper> getDevicHealthKnowledgeCheckDetailsByImei(@RequestParam("text") String text, @RequestParam("page") int page, @RequestParam("size") int size )
	{	
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, deviceHealthPacketService.getDevicHealthKnowledgeCheckDetailsByImei(text, page, size)),
				HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<ResponseWrapper> getSearchByImei(@RequestParam("text") String text, @RequestParam("page") int page, @RequestParam("size") int size) {
		
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, deviceHealthPacketService.getSearchByColumns(text,page,size)), HttpStatus.OK);
	}
	
	@GetMapping("/filterByCompany")
	public ResponseEntity<ResponseWrapper> getFilteredDataByCompany(@RequestParam(value = "date", required = false) String date, @RequestParam(value = "oemId", required = true) int oemId, @RequestParam("page") int page, @RequestParam("size") int size) {
		
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, deviceHealthPacketService.getFilteredDataByCompany(date, oemId, page, size)), HttpStatus.OK);
	}
	
	@GetMapping("/filterByRto")
	public ResponseEntity<ResponseWrapper> getFilteredDataByRto(@RequestParam(value = "date", required = false) String date, @RequestParam(value = "rtoLocationId", required = true) int rtoLocationId) {
		
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, deviceHealthPacketService.getFilteredDataByRto(date, rtoLocationId)), HttpStatus.OK);
	}
	
	@GetMapping("/getHealthRawDataByImei")
	public ResponseEntity<ResponseWrapper> getHealthRawDataByImei(@RequestParam("imei") String imei) {
		
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, deviceHealthPacketService.getHealthRawDataByImei(imei)), HttpStatus.OK);
	}
	
}
