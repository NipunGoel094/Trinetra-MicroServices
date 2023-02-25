package com.trinetra.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.trinetra.model.entity.RawData;
import com.trinetra.repo.RawDataRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RawDataService {

	@Autowired
	private RawDataRepo rawDataRepo;

	public Page<RawData> getAllRawData(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("created_at").descending());
		Page<RawData> allrawData = rawDataRepo.getAllRawData(pageable);

		return allrawData;
	}

	public List<RawData> getImei(String imei) {
		RawData rd = null;
		List<RawData> list = new ArrayList<RawData>();
		Object[][] devicedata = rawDataRepo.fingByImei(imei);
		for (int i = 0; i < devicedata.length; i++) {
			if (devicedata.length >= 0) {
				rd = new RawData();
				rd.setRaw_data(String.valueOf(devicedata[i][0]));
				String time = String.valueOf(devicedata[i][1]);
				Timestamp ts = Timestamp.valueOf(time);
				rd.setCreated_at(ts);
				rd.setId(Integer.parseInt(String.valueOf(devicedata[i][2])));
				list.add(rd);
			}
		}
		log.debug("size of rowdata {} getImei", list.size());
		return list;
	}
	public List<RawData> getImeiNo(String imeiNo) {
		RawData rd = null;
		List<RawData> list = new ArrayList<RawData>();
		Object[][] devicedata = rawDataRepo.fingByImeiNo(imeiNo);
		for (int i = 0; i < devicedata.length; i++) {
			if (devicedata.length >= 0) {
				rd = new RawData();
				rd.setRaw_data(String.valueOf(devicedata[i][0]));
				String time = String.valueOf(devicedata[i][1]);
				Timestamp ts = Timestamp.valueOf(time);
				rd.setCreated_at(ts);
				rd.setId(Integer.parseInt(String.valueOf(devicedata[i][2])));
				list.add(rd);
			}
		}
		log.debug("size of rowdata {} getImei", list.size());
		return list;
	}
	

	public List<RawData> getRawDataByImei(String imei) {
		RawData rd = null;
		List<RawData> list = new ArrayList<RawData>();
		Object[][] rawdata = rawDataRepo.getRawDataByImei(imei);
		for (int i = 0; i < rawdata.length; i++) {
			if (rawdata.length >= 0) {
				rd = new RawData();
				rd.setRaw_data(String.valueOf(rawdata[i][0]));
				String time = String.valueOf(rawdata[i][1]);
				Timestamp ts = Timestamp.valueOf(time);
				rd.setCreated_at(ts);
				rd.setId(Integer.parseInt(String.valueOf(rawdata[i][2])));
				list.add(rd);
			}
		}
		log.debug("size of rowdata {} getRawDataByImei", list.size());
		return list;
	}

	public Page<RawData> getRawDataBasedOnImeiOrDate(String input, int page, int size) {
		Pageable pageable=PageRequest.of(page, size);
		Page<RawData> rawDetails=rawDataRepo.getRawDataBasedOnImeiOrDate(input,pageable);
		return rawDetails;
	}

	

}
