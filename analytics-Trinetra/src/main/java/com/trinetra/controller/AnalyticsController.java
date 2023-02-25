package com.trinetra.controller;

import static com.trinetra.model.enums.Status.SUCCESS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trinetra.model.entity.CurrentVehicleRegNo;
import com.trinetra.model.entity.DownVehicle;
import com.trinetra.model.entity.GroupViewVehicle;
import com.trinetra.model.entity.LiveVehicle;
import com.trinetra.model.wrapper.ResponseWrapper;

import com.trinetra.service.AnalyticsService;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {
	
	@Autowired
	private	AnalyticsService  analyticservice;
	
	@GetMapping("/getDownvehicle")
	public ResponseEntity<ResponseWrapper> findAllDownvehicle(@RequestParam("page") int page, @RequestParam("size") int size ) {
		Page<DownVehicle> getList=analyticservice.getDownvehicleDetails(page,size);
		
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, getList), HttpStatus.OK);
	}
	
	@GetMapping("/getDownvehicleByRto")
	public ResponseEntity<ResponseWrapper> findAllDownvehicleByRto(@RequestParam("entity") int option, @RequestParam("id") int id, @RequestParam("page") int page, @RequestParam("size") int size) {
		Page<DownVehicle> getList=analyticservice.getDownvehicleDetailsByRtoId(option,id,page,size);
		
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, getList), HttpStatus.OK);
	}
	
	@GetMapping("/getDownvehicleByVltdByDays")
	public ResponseEntity<ResponseWrapper> findAllDownvehicleByVltdByDays(@RequestParam("days") int days, @RequestParam("vltdManufacturerName") String vltdManufacturerName, @RequestParam("page") int page, @RequestParam("size") int size) {
		Page<DownVehicle> getList=analyticservice.getDownvehicleByVltdByDays(days,vltdManufacturerName,page,size);
		
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, getList), HttpStatus.OK);
	}
	
	@GetMapping("/searchByColumns")
	public ResponseEntity<ResponseWrapper> searchByColumns(@RequestParam("text") String text, @RequestParam("page") int page, @RequestParam("size") int size) {
		Object getList=analyticservice.getSearchResults(text,page,size);
		
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, getList), HttpStatus.OK);
	}
	
	@GetMapping("/getLiveEmergeny")
	public ResponseEntity<ResponseWrapper> findAllLiveEmergency(@RequestParam("page") int page, @RequestParam("size") int size) {
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, analyticservice.getLiveEmergencyDetails(page, size)), HttpStatus.OK);
	}
	
	@GetMapping("/getLiveEmergenyByColumns")
	public ResponseEntity<ResponseWrapper> findAllLiveEmergencyByColumns(@RequestParam("text") String text, @RequestParam("page") int page, @RequestParam("size") int size) {
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, analyticservice.getLiveEmergencyDetailsByColumns(text,page,size)), HttpStatus.OK);
	}
	
	@GetMapping("/getConnectedDevices")
	public ResponseEntity<ResponseWrapper> findAllConnectedDevices(@RequestParam("page") int page, @RequestParam("size") int size) {
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, analyticservice.getConnectedDeviceDetailsList(page, size)), HttpStatus.OK);
	}
	
	@GetMapping("/getConnectedDevicesByChassisNo")
	public ResponseEntity<ResponseWrapper> findAllConnectedDevicesByChassisNo(@RequestParam("text") String text, @RequestParam("page") int page, @RequestParam("size") int size) {
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, analyticservice.getConnectedDeviceDetailsByChassisNo(text,page,size)), HttpStatus.OK);
	}
	
	@GetMapping("/getConnectedDevicesByPollingTime")
	public ResponseEntity<ResponseWrapper> findAllConnectedDevicesByPollingTime(@RequestParam("pollingHour") int pollingHour, @RequestParam("page") int page, @RequestParam("size") int size) {
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, analyticservice.getConnectedDeviceDetailsByPollingTime(pollingHour,page,size)), HttpStatus.OK);
	}
	
	@GetMapping("/getConnectedDevicesByPort")
	public ResponseEntity<ResponseWrapper> findAllConnectedDevicesByPort(@RequestParam("portId") int portId) {
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, analyticservice.getConnectedDeviceDetailsByPortId(portId)), HttpStatus.OK);
	}
	
	@GetMapping("/getAllPorts")
	public ResponseEntity<ResponseWrapper> findAllPorts() {
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, analyticservice.getAllPortsWithId()), HttpStatus.OK);
	}
	
	@GetMapping("/getStateERSData")
	public ResponseEntity<ResponseWrapper> getStateERSData(@RequestParam("page") int page, @RequestParam("size") int size) {
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, analyticservice.getStateERSData(page, size)), HttpStatus.OK);
	}
	
	@GetMapping("/getStateERSDataByState")
	public ResponseEntity<ResponseWrapper> getStateERSDataByState(@RequestParam("stateName") String stateName, @RequestParam("page") int page, @RequestParam("size") int size) {
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, analyticservice.getStateERSDataByState(stateName,page, size)), HttpStatus.OK);
	}
	
	@GetMapping("/searchStateERSDataByColumns")
	public ResponseEntity<ResponseWrapper> searchStateERSDataByColumns(@RequestParam("text") String text, @RequestParam("page") int page, @RequestParam("size") int size) {
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, analyticservice.searchStateERSDataByColumns(text,page, size)), HttpStatus.OK);
	}
	
	@GetMapping("/listOfCurrentVehicleRegNo")
	public ResponseEntity<ResponseWrapper> getlistOfCurrentVehicleRegNo(@RequestParam("vehicleRegNo") String vehicleRegNo) {
		List<CurrentVehicleRegNo> vehicleRegList = analyticservice.getlistOfCurrentDateVehicleRegNo(vehicleRegNo);
		Map<String, List<CurrentVehicleRegNo>> vehicleResponseMap = new HashMap<String, List<CurrentVehicleRegNo>>();
		vehicleResponseMap.put("content", vehicleRegList);
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, vehicleResponseMap), HttpStatus.OK);
	}
	
	@GetMapping("/liveVehicle")
	public ResponseEntity<ResponseWrapper> getLiveVehicleResponse(@RequestParam("vehicleRegNo") String vehicleRegNo) {
		LiveVehicle vehicleResponse = analyticservice.getVehiclePlotResponse(vehicleRegNo);
		Map<String, LiveVehicle> vehiclePlotResponse = new HashMap<String, LiveVehicle>();
		vehiclePlotResponse.put("content", vehicleResponse);
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, vehiclePlotResponse),
				HttpStatus.OK);
	}
	
	@GetMapping("/getCountsByStateAndRto")
	public ResponseEntity<ResponseWrapper> getCountsByStateAndRto(@RequestParam("stateId") int stateId,@RequestParam("rtoId") int rtoId){
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name,analyticservice.getCountsByStateAndRto(stateId,rtoId)),HttpStatus.OK);
	}
	
	@GetMapping("/getDetailsByStateIdAndRto")
	public ResponseEntity<ResponseWrapper> getDetailsByStateIdAndRto(@RequestParam("stateId") int stateId,@RequestParam("rtoId") int rtoId){
		List<GroupViewVehicle> response=analyticservice.getDetailsByStateIdAndRto(stateId,rtoId);
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name,response),HttpStatus.OK);
	}
}
