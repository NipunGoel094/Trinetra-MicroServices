package com.trinetra.service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.trinetra.exception.CustomException;
import com.trinetra.model.entity.RawData;
import com.trinetra.model.page.response.RenewalDeviceDetailsResponse;
import com.trinetra.model.response.DeviceMasterModelResponse;
import com.trinetra.repo.CheckRepo;
import com.trinetra.repo.RenewalDevicesRepo;
import com.trinetra.repo.VehicleActivationRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CheckService {

	@Autowired
	private CheckRepo checkRepo;
	
	@Autowired
	private VehicleActivationRepo vehicleActivationRepo;
	
	@Autowired
	private RenewalDevicesRepo renewalDevicesRepo;
	
	@Autowired
	private RawDataService rawDataService;
	
	public Page<DeviceMasterModelResponse> getAllDeviceData(int page, int size) throws CustomException {
		List<DeviceMasterModelResponse> resultList = new ArrayList<>();
		Pageable pageable = PageRequest.of(page, size);
		try {
			int totalRowCount = vehicleActivationRepo.getDeviceListCount();
			List<Object[]> data = null;
			Page<Object[]> list = vehicleActivationRepo.getDeviceList(pageable);
			if (!list.isEmpty()) {

				data = list.getContent();
				for (int i = 0; i < data.size(); i++) {

					DeviceMasterModelResponse deviceMasterModelResponse = new DeviceMasterModelResponse();

					deviceMasterModelResponse.setDeviceId(Integer.parseInt(String.valueOf(data.get(i)[0])));
					if (String.valueOf(data.get(i)[1]) != null && String.valueOf(data.get(i)[1]) != "null") {
						deviceMasterModelResponse.setDeviceImei(String.valueOf(data.get(i)[1]));
					} else {
						deviceMasterModelResponse.setDeviceImei("");
					}
					if (String.valueOf(data.get(i)[2]) != null && String.valueOf(data.get(i)[2]) != "null") {
						deviceMasterModelResponse.setDeviceSrNo(String.valueOf(data.get(i)[2]));
					} else {
						deviceMasterModelResponse.setDeviceSrNo("");
					}
					if (String.valueOf(data.get(i)[3]) != null && String.valueOf(data.get(i)[3]) != "null") {
						deviceMasterModelResponse.setDeviceIccid(String.valueOf(data.get(i)[3]));
					} else {
						deviceMasterModelResponse.setDeviceIccid("");
					}
					if (String.valueOf(data.get(i)[4]) != null && String.valueOf(data.get(i)[4]) != "null") {
						deviceMasterModelResponse.setNetworkProvider1(String.valueOf(data.get(i)[4]));
					} else {
						deviceMasterModelResponse.setNetworkProvider1("");
					}
					if (String.valueOf(data.get(i)[5]) != null && String.valueOf(data.get(i)[5]) != "null") {
						deviceMasterModelResponse.setVehicleRegNo(String.valueOf(data.get(i)[5]));
					} else {
						deviceMasterModelResponse.setVehicleRegNo("");
					}

					if (String.valueOf(data.get(i)[6]) != null && String.valueOf(data.get(i)[6]) != "null") {
						deviceMasterModelResponse.setChassisNo(String.valueOf(data.get(i)[6]));
					} else {
						deviceMasterModelResponse.setChassisNo("");
					}
					if (String.valueOf(data.get(i)[7]) != null && String.valueOf(data.get(i)[7]) != "null") {
						deviceMasterModelResponse.setRtoName(String.valueOf(data.get(i)[7]));
					} else {
						deviceMasterModelResponse.setRtoName("");
					}
					if (String.valueOf(data.get(i)[8]) != null && String.valueOf(data.get(i)[8]) != "null") {
						deviceMasterModelResponse.setPermitHolderName(String.valueOf(data.get(i)[8]));
					} else {
						deviceMasterModelResponse.setPermitHolderName("");
					}
					if (String.valueOf(data.get(i)[9]) != null && String.valueOf(data.get(i)[9]) != "null") {
						deviceMasterModelResponse.setCompanyName(String.valueOf(data.get(i)[9]));
					} else {
						deviceMasterModelResponse.setCompanyName("");
					}
					if (String.valueOf(data.get(i)[10]) != null && String.valueOf(data.get(i)[10]) != "null") {
						deviceMasterModelResponse.setDealerName(String.valueOf(data.get(i)[10]));
					} else {
						if (String.valueOf(data.get(i)[9]) != null && String.valueOf(data.get(i)[9]) != "null") 
						{    
							 deviceMasterModelResponse.setDealerName("");
								List<Object> deafultDealerForOEMList =  checkRepo.findDeafultDealerForOEM((BigInteger)data.get(i)[15]);
							 if(deafultDealerForOEMList.size()>0)
							 {
								 deviceMasterModelResponse.setDealerName((String) deafultDealerForOEMList.get(0)); 
							 }
							 
						} else {
							deviceMasterModelResponse.setDealerName("");
						}
					}
					String deviceStatus = "";
					
					if (String.valueOf(data.get(i)[12]) != null && String.valueOf(data.get(i)[12]) != "null") {
						deviceStatus = "Pending";
					}
					deviceMasterModelResponse.setDeviceStatus(deviceStatus);
					if (String.valueOf(data.get(i)[13]) != null && String.valueOf(data.get(i)[13]) != "null") {
						deviceMasterModelResponse.setMobileNo(String.valueOf(data.get(i)[13]));
					} else {
						deviceMasterModelResponse.setMobileNo("");
					}
                  String vehicleStatus = "Not Assigned";
					
					if (String.valueOf(data.get(i)[14]) != null && String.valueOf(data.get(i)[14]) != "null") {
						vehicleStatus = "Assigned";
					}
					
					deviceMasterModelResponse.setVehicleStatus(vehicleStatus);

					if (String.valueOf(data.get(i)[16]) != null && String.valueOf(data.get(i)[16]) != "null") {
						deviceMasterModelResponse.setVehicleTypeId(String.valueOf(data.get(i)[16]));
					} else {						 
						deviceMasterModelResponse.setVehicleTypeId("");
					}
					 
					if (String.valueOf(data.get(i)[17]) != null && String.valueOf(data.get(i)[17]) != "null") {
						deviceMasterModelResponse.setCustomerId(String.valueOf(data.get(i)[17]));
					} else {						 
						deviceMasterModelResponse.setCustomerId("");
					}
					if (String.valueOf(data.get(i)[18]) != null && String.valueOf(data.get(i)[18]) != "null") {
						deviceMasterModelResponse.setVdmId(Integer.parseInt(String.valueOf(data.get(i)[18])));
					} else {						 
						deviceMasterModelResponse.setVdmId(0);
					}

					List<RawData> rawDatas = rawDataService.getImeiNo(deviceMasterModelResponse.getDeviceImei());
					String deviceConnectedStatus = "Not Connected";
					if (!rawDatas.isEmpty() && null != rawDatas)
						deviceConnectedStatus = "Connected";

					deviceMasterModelResponse.setPollingStatus(deviceConnectedStatus);
					resultList.add(deviceMasterModelResponse);

				}
			} else {
				throw new CustomException("No Device data found!");
			}
			Page<DeviceMasterModelResponse> paginatedResponses = new PageImpl<>(resultList, pageable, totalRowCount);
			return paginatedResponses;
		} catch (Exception e) {
			log.error("Exception occurred while fetching data: {}", e.getMessage());
			e.printStackTrace();
			Page<DeviceMasterModelResponse> paginatedResponses = new PageImpl<>(resultList, pageable, 0);
			return paginatedResponses;
		}

	}
	
	public Page<DeviceMasterModelResponse> getAllSearchDeviceData(int page, int size, String searchBy) {
		List<DeviceMasterModelResponse> resultList = new ArrayList<>();
		Pageable pageable = PageRequest.of(page, size);
		try {
			if(searchBy == null || searchBy.isBlank()) {
				throw new CustomException("Empty String Search Not Allowed!!");
			}
			List<Object[]> data = null;
			Page<Object[]> list = vehicleActivationRepo.getDeviceWithSearchList(pageable, searchBy);
			long totalRowCount = list.getTotalElements();
			if (!list.isEmpty()) {

				data = list.getContent();
				for (int i = 0; i < data.size(); i++) {

					DeviceMasterModelResponse deviceMasterModelResponse = new DeviceMasterModelResponse();

					deviceMasterModelResponse.setDeviceId(Integer.parseInt(String.valueOf(data.get(i)[0])));
					if (String.valueOf(data.get(i)[1]) != null && String.valueOf(data.get(i)[1]) != "null") {
						deviceMasterModelResponse.setDeviceImei(String.valueOf(data.get(i)[1]));
					} else {
						deviceMasterModelResponse.setDeviceImei("");
					}
					if (String.valueOf(data.get(i)[2]) != null && String.valueOf(data.get(i)[2]) != "null") {
						deviceMasterModelResponse.setDeviceSrNo(String.valueOf(data.get(i)[2]));
					} else {
						deviceMasterModelResponse.setDeviceSrNo("");
					}
					if (String.valueOf(data.get(i)[3]) != null && String.valueOf(data.get(i)[3]) != "null") {
						deviceMasterModelResponse.setDeviceIccid(String.valueOf(data.get(i)[3]));
					} else {
						deviceMasterModelResponse.setDeviceIccid("");
					}
					if (String.valueOf(data.get(i)[4]) != null && String.valueOf(data.get(i)[4]) != "null") {
						deviceMasterModelResponse.setNetworkProvider1(String.valueOf(data.get(i)[4]));
					} else {
						deviceMasterModelResponse.setNetworkProvider1("");
					}
					if (String.valueOf(data.get(i)[5]) != null && String.valueOf(data.get(i)[5]) != "null") {
						deviceMasterModelResponse.setVehicleRegNo(String.valueOf(data.get(i)[5]));
					} else {
						deviceMasterModelResponse.setVehicleRegNo("");
					}

					if (String.valueOf(data.get(i)[6]) != null && String.valueOf(data.get(i)[6]) != "null") {
						deviceMasterModelResponse.setChassisNo(String.valueOf(data.get(i)[6]));
					} else {
						deviceMasterModelResponse.setChassisNo("");
					}
					if (String.valueOf(data.get(i)[7]) != null && String.valueOf(data.get(i)[7]) != "null") {
						deviceMasterModelResponse.setRtoName(String.valueOf(data.get(i)[7]));
					} else {
						deviceMasterModelResponse.setRtoName("");
					}
					if (String.valueOf(data.get(i)[8]) != null && String.valueOf(data.get(i)[8]) != "null") {
						deviceMasterModelResponse.setPermitHolderName(String.valueOf(data.get(i)[8]));
					} else {
						deviceMasterModelResponse.setPermitHolderName("");
					}
					if (String.valueOf(data.get(i)[9]) != null && String.valueOf(data.get(i)[9]) != "null") {
						deviceMasterModelResponse.setCompanyName(String.valueOf(data.get(i)[9]));
					} else {
						deviceMasterModelResponse.setCompanyName("");
					}
					if (String.valueOf(data.get(i)[10]) != null && String.valueOf(data.get(i)[10]) != "null") {
						deviceMasterModelResponse.setDealerName(String.valueOf(data.get(i)[10]));
					} else {
						if (String.valueOf(data.get(i)[9]) != null && String.valueOf(data.get(i)[9]) != "null") 
						{    
							 deviceMasterModelResponse.setDealerName("");
								List<Object> deafultDealerForOEMList =  checkRepo.findDeafultDealerForOEM((BigInteger)data.get(i)[15]);
							 if(deafultDealerForOEMList.size()>0)
							 {
								 deviceMasterModelResponse.setDealerName((String) deafultDealerForOEMList.get(0)); 
							 }
							 
						} else {
							deviceMasterModelResponse.setDealerName("");
						}
					}
					String deviceStatus = "";
					
					if (String.valueOf(data.get(i)[12]) != null && String.valueOf(data.get(i)[12]) != "null") {
						deviceStatus = "Pending";
					}
					deviceMasterModelResponse.setDeviceStatus(deviceStatus);
					if (String.valueOf(data.get(i)[13]) != null && String.valueOf(data.get(i)[13]) != "null") {
						deviceMasterModelResponse.setMobileNo(String.valueOf(data.get(i)[13]));
					} else {
						deviceMasterModelResponse.setMobileNo("");
					}
                  String vehicleStatus = "Not Assigned";
					
					if (String.valueOf(data.get(i)[14]) != null && String.valueOf(data.get(i)[14]) != "null") {
						vehicleStatus = "Assigned";
					}
					
					deviceMasterModelResponse.setVehicleStatus(vehicleStatus);

					if (String.valueOf(data.get(i)[16]) != null && String.valueOf(data.get(i)[16]) != "null") {
						deviceMasterModelResponse.setVehicleTypeId(String.valueOf(data.get(i)[16]));
					} else {						 
						deviceMasterModelResponse.setVehicleTypeId("");
					}
					 
					if (String.valueOf(data.get(i)[17]) != null && String.valueOf(data.get(i)[17]) != "null") {
						deviceMasterModelResponse.setCustomerId(String.valueOf(data.get(i)[17]));
					} else {						 
						deviceMasterModelResponse.setCustomerId("");
					}
					if (String.valueOf(data.get(i)[18]) != null && String.valueOf(data.get(i)[18]) != "null") {
						deviceMasterModelResponse.setVdmId(Integer.parseInt(String.valueOf(data.get(i)[18])));
					} else {						 
						deviceMasterModelResponse.setVdmId(0);
					}

					List<RawData> rawDatas = rawDataService.getImeiNo(deviceMasterModelResponse.getDeviceImei());
					String deviceConnectedStatus = "Not Connected";
					if (!rawDatas.isEmpty() && null != rawDatas)
						deviceConnectedStatus = "Connected";

					deviceMasterModelResponse.setPollingStatus(deviceConnectedStatus);
					resultList.add(deviceMasterModelResponse);

				}
			} else {
				throw new CustomException("No Device data found!");
			}
			Page<DeviceMasterModelResponse> paginatedResponses = new PageImpl<>(resultList, pageable, totalRowCount);
			return paginatedResponses;
		} catch (Exception e) {
			log.error("Exception occurred while fetching data: {}", e.getMessage());
			e.printStackTrace();
			Page<DeviceMasterModelResponse> paginatedResponses = new PageImpl<>(resultList, pageable, 0);
			return paginatedResponses;
		}
	}
	
	public Page<RenewalDeviceDetailsResponse> getRenewalDevicesDetails(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		List<Object[]> data = null;
		Page<Object[]> list = renewalDevicesRepo.getRenewalDevicesDetails(pageable);
		long totalRowCount = list.getTotalElements();
		List<RenewalDeviceDetailsResponse> resultList = new ArrayList<>();
	 
		if (null != list && !list.isEmpty()) {
			data = list.getContent();
			for (int i = 0; i < data.size(); i++) {	
				
				RenewalDeviceDetailsResponse renewalDeviceDetailsResponse = new RenewalDeviceDetailsResponse();
				
				renewalDeviceDetailsResponse.setDevice_Sr_No((String)data.get(i)[0]);
				renewalDeviceDetailsResponse.setDevice_IMEI_No((String)data.get(i)[1]);
				renewalDeviceDetailsResponse.setDevice_ICCID_No((String)data.get(i)[2]);
				renewalDeviceDetailsResponse.setVLTD_Manufacturer((String)data.get(i)[3]);
				renewalDeviceDetailsResponse.setDealer_Name((String)data.get(i)[4]);
				renewalDeviceDetailsResponse.setVehicle_Reg_No((String)data.get(i)[5]);
				renewalDeviceDetailsResponse.setVehicle_Chassis_No((String)data.get(i)[6]);
				renewalDeviceDetailsResponse.setRTO_Name((String)data.get(i)[7]);
				renewalDeviceDetailsResponse.setPermit_Holder_Name((String)data.get(i)[8]);
				renewalDeviceDetailsResponse.setPermit_Holder_MobileNo((String)data.get(i)[9]);
				renewalDeviceDetailsResponse.setPolling_Status((String)data.get(i)[10]);
				renewalDeviceDetailsResponse.setRenewal_Date(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));;
				renewalDeviceDetailsResponse.setNext_Renewal_Date(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				renewalDeviceDetailsResponse.setRenewal_Status("Active");
				renewalDeviceDetailsResponse.setESIM_Provider("NA");
				
				resultList.add(renewalDeviceDetailsResponse);				
			}
			
		}
		Page<RenewalDeviceDetailsResponse> paginatedResponses = new PageImpl<>(resultList, pageable, totalRowCount);
		return paginatedResponses;
	}

	public Page<RenewalDeviceDetailsResponse> getSearchRenewalDevicesDetails(int page, int size, String searchBy) {
		Pageable pageable = PageRequest.of(page, size);
		List<Object[]> data = null;
		Page<Object[]> list = renewalDevicesRepo.getSearchRenewalDevicesDetails(pageable, searchBy);	
		long totalRowCount = list.getTotalElements();
		List<RenewalDeviceDetailsResponse> resultList = new ArrayList<>();
	 
		if (null != list && !list.isEmpty()) {
			data = list.getContent();
			for (int i = 0; i < data.size(); i++) {	
				
				RenewalDeviceDetailsResponse renewalDeviceDetailsResponse = new RenewalDeviceDetailsResponse();
				
				renewalDeviceDetailsResponse.setDevice_Sr_No((String)data.get(i)[0]);
				renewalDeviceDetailsResponse.setDevice_IMEI_No((String)data.get(i)[1]);
				renewalDeviceDetailsResponse.setDevice_ICCID_No((String)data.get(i)[2]);
				renewalDeviceDetailsResponse.setVLTD_Manufacturer((String)data.get(i)[3]);
				renewalDeviceDetailsResponse.setDealer_Name((String)data.get(i)[4]);
				renewalDeviceDetailsResponse.setVehicle_Reg_No((String)data.get(i)[5]);
				renewalDeviceDetailsResponse.setVehicle_Chassis_No((String)data.get(i)[6]);
				renewalDeviceDetailsResponse.setRTO_Name((String)data.get(i)[7]);
				renewalDeviceDetailsResponse.setPermit_Holder_Name((String)data.get(i)[8]);
				renewalDeviceDetailsResponse.setPermit_Holder_MobileNo((String)data.get(i)[9]);
				renewalDeviceDetailsResponse.setPolling_Status((String)data.get(i)[10]);
				renewalDeviceDetailsResponse.setRenewal_Date(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));;
				renewalDeviceDetailsResponse.setNext_Renewal_Date(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				renewalDeviceDetailsResponse.setRenewal_Status("Active");
				renewalDeviceDetailsResponse.setESIM_Provider("NA");
				
				resultList.add(renewalDeviceDetailsResponse);				
			}
			
		}
		Page<RenewalDeviceDetailsResponse> paginatedResponses = new PageImpl<>(resultList, pageable, totalRowCount);
		return paginatedResponses;
	}
}
