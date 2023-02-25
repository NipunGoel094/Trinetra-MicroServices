package com.trinetra.model.page.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface MobileHistoryLogReportResponse {

	public String getVltdName();
	public String getDealerName();
	public String getPermitHolderName();
	public String getRtoName();
	public String getDeviceImei();
	public String getDeviceUin();
	public String getDeviceIccid();
	public String getVehicleNo();
	public String getAlert();
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
	public Date getPollingDateTime();
	public String getSpeed();
	public String getEventLat();
	public String getIgnitionStatus();
	public String getVehicleStatus();
	public String getGpsStatus();
	public String getEventLong();
	public String getEventLocation();
}
