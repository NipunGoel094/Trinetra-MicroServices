package com.trinetra.model.page.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface MobileRouteDeviationVehicleData {

	public String getRouteId();
	public String getVehicleRegNo();
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
	public Date getStartTime();
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
	public Date getEndTime();
	public String getRouteName();
	public String getDeviceImei();
	public String getVltdName();
	public String getPermitHolderName();
	public String getPermitHolderMobile();

}
