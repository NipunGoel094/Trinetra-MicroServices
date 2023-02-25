package com.trinetra.model.page.response;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface MobileConnectedDeviceDetailsResponse {
	public String getVehicleRegNo();
	public String getVehicleChassisNo();
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
	public Date getPollingTime();
	public BigDecimal getLat();
	public BigDecimal getLon();
	public String getLocation();
	public String getSpeed();
	public String getDeviceImei();
	public String getVltdManufacturerName();
	public String getRtoName();
	public Boolean getIgnition();
}
