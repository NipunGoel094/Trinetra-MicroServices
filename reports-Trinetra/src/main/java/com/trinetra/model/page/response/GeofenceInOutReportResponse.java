package com.trinetra.model.page.response;

public interface GeofenceInOutReportResponse {

	public String getVltdName();
	public String getDealerName();
	public String getPermitHolderName();
	public String getDeviceImei();
	public String getDeviceIccid();
	public String getVehicleRegNo();
	public String getAlertDate();
	public String getInLat();
	public String getInLon();
	public String getOutLat();
	public String getOutLon();
	public String getInTime();
	public String getOutTime();
	public String getPoiName();
	public String getInDistance();
	public String getOutDistance();
	public String getStatus();
}
