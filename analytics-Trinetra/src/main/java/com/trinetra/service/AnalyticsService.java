package com.trinetra.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.trinetra.model.entity.ConnectedDeviceDetails;
import com.trinetra.model.entity.CurrentVehicleRegNo;
import com.trinetra.model.entity.DownVehicle;
import com.trinetra.model.entity.GroupViewVehicle;
import com.trinetra.model.entity.LiveEmergency;
import com.trinetra.model.entity.LiveVehicle;
import com.trinetra.model.entity.StateERSData;
import com.trinetra.model.page.response.AlertTypeCounts;
import com.trinetra.model.page.response.ConnectedDevicesDetailsResponse;
import com.trinetra.model.page.response.GetNonPollingAndPendingAlert;
import com.trinetra.repo.AnalyticsRepo;
import com.trinetra.repo.ConnectedDeviceDetailsRepo;
import com.trinetra.repo.CurrentVehicleRegNoRepo;
import com.trinetra.repo.DownVehicleRepo;
import com.trinetra.repo.GroupViewVehicleRepo;
import com.trinetra.repo.LiveEmergencyRepo;
import com.trinetra.repo.LiveVehicleRepo;
import com.trinetra.repo.StateERSDataRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AnalyticsService {

	@Autowired
	private AnalyticsRepo analyticsRepo;
	
	@Autowired
	private DownVehicleRepo downVehicleRepo;
	
	@Autowired
	private LiveEmergencyRepo liveEmergencyRepo;
	
	@Autowired
	private ConnectedDeviceDetailsRepo connectedDeviceDetailsRepo;
	
	@Autowired
	private StateERSDataRepo stateERSDataRepo;
	
	@Autowired
	private CurrentVehicleRegNoRepo currentVehicleRegNoRepo;
	
	@Autowired
	private LiveVehicleRepo liveVehicleRepo;
	
	@Autowired
	private GroupViewVehicleRepo groupViewVehicleRepo;
	
	public Page<DownVehicle> getDownvehicleDetails(int page,int size) {
		Pageable pageable=PageRequest.of(page, size);
		Page<DownVehicle> downVehicleResponceList=downVehicleRepo.findAll(pageable);
		return downVehicleResponceList;
		
	}

	public Page<DownVehicle> getDownvehicleDetailsByRtoId(int option, int id, int page, int size) {
		Pageable pageable=PageRequest.of(page, size);
		if(option == 270) {
			return downVehicleRepo.getDownVehicleListByRtoId(id,pageable);
		}
		else if(option == 326) {
			return downVehicleRepo.getDownVehicleListByPermitHolderId(id, pageable);
		}
		else {
			return downVehicleRepo.getDownVehicleListByVltdId(id, pageable);
		}
		
	}
	
	public Page<DownVehicle> getDownvehicleByVltdByDays(int days, String vltdManufacturerName, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<DownVehicle> downVehiclesByVltdByDays = downVehicleRepo.getDownvehicleByVltdByDays(days,vltdManufacturerName,pageable);
		return downVehiclesByVltdByDays;
	}
	
	public Object getSearchResults(String text, int page, int size) {
		try {
			Pageable pageable = PageRequest.of(page, size);
			if(text == null || text.isBlank()) {
				log.error("Exception occurred - Reason: {}", "Empty Search Not Allowed!!");
				return "Empty Search Not Allowed!!";
			}
			Page<DownVehicle> downVehicleResponceList = downVehicleRepo.getSearchResults(text,pageable);
			return downVehicleResponceList;
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error("Exception occurred - Reason: {}", e.getLocalizedMessage());
			return "Error in Retrieving Data";
		}
		
	}
	
	public Page<LiveEmergency> getLiveEmergencyDetails(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return liveEmergencyRepo.findAll(pageable);
	}
	
	public Object getLiveEmergencyDetailsByColumns(String text, int page, int size) {
		if(text != null && !text.isBlank()) {
			Pageable pageable = PageRequest.of(page, size);
			return liveEmergencyRepo.getLiveEmergencyListByColumns(text,pageable);
		}
		log.error("error in AnalyticsService : {}", "Empty Search Not Allowed!!");
		return "Empty Search Not Allowed!!";
	}
	
	public Page<ConnectedDeviceDetails> getConnectedDeviceDetailsList(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return connectedDeviceDetailsRepo.findAll(pageable);
	}
	
	public Object getConnectedDeviceDetailsByChassisNo(String text, int page, int size) {
		if(text != null && !text.isBlank()) {
			Pageable pageable = PageRequest.of(page, size);
			return connectedDeviceDetailsRepo.getConnectedDevicesByChassisNo(text,pageable);
		}
		log.error("error in AnalyticsService : {}", "Empty Search Not Allowed!!");
		return "Empty Search Not Allowed!!";
	}
	
	public Page<ConnectedDeviceDetails> getConnectedDeviceDetailsByPollingTime(int pollingHour, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		if (pollingHour == 5) {
			return connectedDeviceDetailsRepo.getConnectedDevicesByPollingForToday(pageable);
		} else {
			return connectedDeviceDetailsRepo.getConnectedDevicesByPollingHour(pollingHour, pageable);
		}
	}
	

	public List<ConnectedDevicesDetailsResponse> getConnectedDeviceDetailsByPortId(int portId) {
		return connectedDeviceDetailsRepo.getConnectedDevicesByPortId(portId);
	}
	
	public List<Map<String, Object>> getAllPortsWithId() {
		List<Object[]> allPorts = connectedDeviceDetailsRepo.getListOfPorts();
		List<Map<String,Object>> listOfMaps = new ArrayList<>();
		for(Object[] object : allPorts) {
			Map<String, Object> mapOfPorts = new HashMap<String, Object>();
			mapOfPorts.put("id", object[0]);
			mapOfPorts.put("port", object[1]);
			listOfMaps.add(mapOfPorts);
		}
		return listOfMaps;
	}

	public Page<StateERSData> getStateERSData(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<StateERSData> stateERSDataList = stateERSDataRepo.findAll(pageable);
		return stateERSDataList;
	}

	public Page<StateERSData> getStateERSDataByState(String stateName, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<StateERSData> stateERSDataFilterList = stateERSDataRepo.getStateERSDataByState(stateName,pageable);
		return stateERSDataFilterList;
	}

	public Page<StateERSData> searchStateERSDataByColumns(String text, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		if(text != null && !text.isBlank()) {
			Page<StateERSData> stateERSDataFilterList = stateERSDataRepo.searchStateERSDataByColumns(text,pageable);
			return stateERSDataFilterList;
		}
		List<StateERSData> listOfResponses = new ArrayList<StateERSData>();
		Page<StateERSData> paginatedResponses = new PageImpl<StateERSData>(listOfResponses, pageable, 0);
		return paginatedResponses;
	}

	public List<CurrentVehicleRegNo> getlistOfCurrentDateVehicleRegNo(String vehicleRegNo) {
		return currentVehicleRegNoRepo.getlistOfCurrentDateVehicleRegNo(vehicleRegNo);
	}

	public LiveVehicle getVehiclePlotResponse(String vehicleRegNo) {
		return liveVehicleRepo.getVehiclePlotResponse(vehicleRegNo);
	}

	public Map<String,Object> getCountsByStateAndRto(int stateId, int rtoId) {
		
		Map<String,Object> content=new HashedMap<>();
        List<AlertTypeCounts> alert=analyticsRepo.getAlertCountsByStateAndRto(stateId,rtoId);
		GetNonPollingAndPendingAlert pendingPolling=analyticsRepo.getNonPollingAndPendingAlert(stateId,rtoId);
		content.put("alerts", alert);
		content.put("pending", pendingPolling);
		
		return content;
	}

	public List<GroupViewVehicle> getDetailsByStateIdAndRto(int stateId, int rtoId) {
		return groupViewVehicleRepo.getDetailsByStateIdAndRto(stateId,rtoId);
	}

}
