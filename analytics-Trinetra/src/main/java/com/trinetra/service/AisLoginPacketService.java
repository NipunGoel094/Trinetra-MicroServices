package com.trinetra.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.trinetra.model.entity.LoginPacketCheck;
import com.trinetra.repo.LoginPacketCheckRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AisLoginPacketService {

	@Autowired
	private LoginPacketCheckRepo loginPacketCheckRepo;
	
	public Page<LoginPacketCheck> getAllRecords(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		try {
			return loginPacketCheckRepo.findAll(pageable);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Exception occurred - Reason: {}", e.getLocalizedMessage());
			//throw new CustomException(e.getLocalizedMessage());
		}
		List<LoginPacketCheck> emptyList = new ArrayList<LoginPacketCheck>();
		return new PageImpl<LoginPacketCheck>(emptyList,pageable,0);
	}
	
	public Page<LoginPacketCheck> searchByColumns(String text,int page,int size) {
		Pageable pageable = PageRequest.of(page, size);
		try {
			return loginPacketCheckRepo.getSearchByVehicleRegNoAndDeviceImei(text,pageable);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Exception occurred - Reason: {}", e.getLocalizedMessage());
			//throw new CustomException(e.getLocalizedMessage());
		}
		List<LoginPacketCheck> emptyList = new ArrayList<LoginPacketCheck>();
		return new PageImpl<LoginPacketCheck>(emptyList,pageable,0);
	}
	
	public Page<LoginPacketCheck> filterDataByCompanyName(int oemId, String date,int page,int size) {
		Pageable pageable = PageRequest.of(page, size);
		try {
			if(date == null || date.isBlank()) {
				return loginPacketCheckRepo.getFilterByCompanyNameWithoutDate(oemId,pageable);
			}
			else {
				return loginPacketCheckRepo.getFilterByCompanyNameWithDate(oemId, date,pageable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception occurred - Reason: {}", e.getLocalizedMessage());
			//throw new CustomException(e.getLocalizedMessage());
		}
		List<LoginPacketCheck> emptyList = new ArrayList<LoginPacketCheck>();
		return new PageImpl<LoginPacketCheck>(emptyList,pageable,0);
	}
}
