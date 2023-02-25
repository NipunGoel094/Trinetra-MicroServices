package com.trinetra.model.page.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface MobileDownVehicleResponse {

	public Integer getAlertId();
	public String getVltdName();
	public String getVehicleRegNo();
	public String getChassisNo();
	public String getDeviceSrNo();
	public String getDeviceIccid();
	public String getImeiNo();
	public String getPermitHolderName();
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
	public Date getLastPollingTime();
	public String getLastEmergencyalerttime();
	public int getLastSpeed();
	public String getLastLocation();
	public Boolean getStatus();
	public String getDownLat();
	public String getDownLon();
	public String getRtoName();
	public Boolean getIgnition();
}
