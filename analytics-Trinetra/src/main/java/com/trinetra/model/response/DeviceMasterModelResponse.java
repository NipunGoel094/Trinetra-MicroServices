package com.trinetra.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@ToString
public class DeviceMasterModelResponse {
	private int deviceId;
	private String deviceImei;
	private String deviceIccid;
	private String deviceSrNo;
	private String vehicleRegNo;
	private String chassisNo;
	private String rtoName;
	private String permitHolderName;
	private String MobileNo;
	private String dealerName;
	private String companyName;
	private String vehicleStatus;
	private String deviceStatus;
	private String pollingStatus;
	private String m2mServiceProviderName;
	private Integer stateId;
	private Long deviceCount;
	private String distributerName;
	private String simNumber1;
	private String simNumber2;
	private String modelName;
	private String modelCode;
	private String networkProvider1;
	private String networkProvider2;
	private String vehicleTypeId;
	private String customerId;
	private int vdmId ;
	

}
