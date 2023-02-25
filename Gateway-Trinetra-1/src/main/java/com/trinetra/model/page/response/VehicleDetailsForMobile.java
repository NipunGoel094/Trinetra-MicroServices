package com.trinetra.model.page.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface VehicleDetailsForMobile {

	public int getVehicleId();
	public String getVehicleRegNo();
	public String getVehicleType();
	public String getDeviceImei();
	public String getDeviceIccid();
	public String getChassisNo();
	public String getEngineNo();
	public String getPersonName();
	public String getRtoName();
	public String getDealerName();
	public String getMobileNo();
	public String getLat();
	public String getLon();
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
	public Date getDateTime();
	public String getLocation();
}
