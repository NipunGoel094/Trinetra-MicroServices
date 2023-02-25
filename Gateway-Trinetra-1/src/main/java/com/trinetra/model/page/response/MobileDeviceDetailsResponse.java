package com.trinetra.model.page.response;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface MobileDeviceDetailsResponse {
	
	public String getDeviceImei();
	public String getDeviceSrNo();
	public String getDeviceIccid();
	public String getVehicleRegNo();
	public String getChassisNo();
	public String getRtoName();
	public String getPermitHolderName();
	public String getPrimaryMobileNumber();
	public String getManufacturerName();
	public String getDealerName();
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
	public Date getPollingTime();
	public BigDecimal getLat();
	public BigDecimal getLon();
	public String getLocation();
	public String getStatus();
	public String getSpeed();
	public Boolean getIgnition();
}
