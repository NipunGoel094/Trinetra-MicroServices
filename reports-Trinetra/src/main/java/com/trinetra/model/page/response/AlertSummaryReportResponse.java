package com.trinetra.model.page.response;

public interface AlertSummaryReportResponse {
	public String getVltdName();
	public String getDealerName();
	public String getPersonName();
	public String getRtoName();
	public String getDeviceImei();
	public String getDeviceIccid();
	public String getVehicleRegNo();
	public String getAlertType();
	public String getAlertCount();
	public String getAlertTime();
	public String getLocation();
	public String getAlertLat();
	public String getAlertLon();
	public Boolean getIgnition();
	public Double getSpeed();
}
