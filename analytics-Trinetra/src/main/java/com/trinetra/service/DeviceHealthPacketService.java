package com.trinetra.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.trinetra.deviceHealthPacket.AISHealthPacket;
import com.trinetra.deviceHealthPacket.TFMHealthPacket;
import com.trinetra.exception.CustomException;
import com.trinetra.model.entity.HealthCheck;
import com.trinetra.model.entity.HealthKnowledgeCheck;
import com.trinetra.model.entity.OemMaster;
import com.trinetra.model.entity.RawData;
import com.trinetra.model.response.DeviceMasterModelResponse;
import com.trinetra.repo.HealthCheckRepo;
import com.trinetra.repo.HealthKnowledgeCheckRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DeviceHealthPacketService {

	@Autowired
	private HealthCheckRepo healthCheckRepo;
	
	@Autowired
	private HealthKnowledgeCheckRepo healthKnowledgeCheckRepo;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public Page<HealthCheck> getDevicHealthCheckeDetails(int page, int size) throws CustomException {
		try {
			Pageable pageable = PageRequest.of(page, size);
			return healthCheckRepo.findAll(pageable);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception occurred - Reason: {}", e.getLocalizedMessage());
			throw new CustomException(e.getLocalizedMessage());
		}
	}
	
	public Page<HealthKnowledgeCheck> getDeviceHealthKnowledgeCheckDetails(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		try {
			Page<HealthKnowledgeCheck> paginatedResponses = healthKnowledgeCheckRepo.findAll(pageable);
			if(paginatedResponses != null && !paginatedResponses.isEmpty()) {
				return paginatedResponses;
			}
			else {
				throw new CustomException("No Data Found !!");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error("Exception occurred - Reason: {}", e.getLocalizedMessage());
			List<HealthKnowledgeCheck> responses = new ArrayList<HealthKnowledgeCheck>();
			Page<HealthKnowledgeCheck> paginatedResponses = new PageImpl<HealthKnowledgeCheck>(responses,pageable,0);
			return paginatedResponses;
		}
			
	}
	
	public Page<HealthKnowledgeCheck> getDevicHealthKnowledgeCheckDetailsByImei(String text, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		try {
			if(text == null || text.isBlank()) {
				throw new CustomException("Search String Cannot Be Empty!!");
			}
			Page<HealthKnowledgeCheck> paginatedResponses = healthKnowledgeCheckRepo.getDeviceHealthKnowledgeCheckDetailsByImei(text,pageable);
			if(paginatedResponses != null && !paginatedResponses.isEmpty()) {
				return paginatedResponses;
			}
			else {
				throw new CustomException("No Data Found !!");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error("Exception occurred - Reason: {}", e.getLocalizedMessage());
			List<HealthKnowledgeCheck> responses = new ArrayList<HealthKnowledgeCheck>();
			Page<HealthKnowledgeCheck> paginatedResponses = new PageImpl<HealthKnowledgeCheck>(responses,pageable,0);
			return paginatedResponses;
		}
	}
	
	public Page<HealthCheck> getSearchByColumns(String text, int page, int size) {
		try {
			Pageable pageable = PageRequest.of(page, size);
			return healthCheckRepo.getSearchResults(text,pageable);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception occurred - Reason: {}", e.getLocalizedMessage());
			throw new CustomException(e.getLocalizedMessage());
		}		
	}
	
	public Page<HealthCheck> getFilteredDataByCompany(String date, int oemId, int page, int size) {
		try {	
			Pageable pageable = PageRequest.of(page, size);
			if(date == null || date.isBlank()) {
				return healthCheckRepo.getFilteredDataByCompanyWithoutDate(oemId,pageable);
			}
			else {
				return healthCheckRepo.getFilteredDataByCompanyWithDate(oemId,date,pageable);
	
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception occurred - Reason: {}", e.getLocalizedMessage());
			throw new CustomException(e.getLocalizedMessage());
		}
	}
	
	public ArrayNode getFilteredDataByRto(String date, int rtoLocationId) {
		try {	
			if(date == null || date.isBlank()) {
				return objectMapper.convertValue(healthCheckRepo.getFilteredDataByRtoWithoutDate(rtoLocationId), ArrayNode.class);
			}
			else {
				return objectMapper.convertValue(healthCheckRepo.getFilteredDataByRtoWithDate(rtoLocationId,date), ArrayNode.class);
	
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception occurred - Reason: {}", e.getLocalizedMessage());
			throw new CustomException(e.getLocalizedMessage());
		}
	}
	public String getHealthRawDataByImei(String imei) {
		String msg="";
		try {
			String checkImei=healthCheckRepo.checkImeiExits(imei);
			if(checkImei==null|| checkImei.isBlank()) {
				msg="Data not Found";
				return msg;
			}
			else {
				String data=healthCheckRepo.getHealthRawDataByImei(imei);
				return data;
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("Exception occurred - Reason Data Not Found: {}", e.getLocalizedMessage());
			throw new CustomException(e.getLocalizedMessage());
		}
	}
}
