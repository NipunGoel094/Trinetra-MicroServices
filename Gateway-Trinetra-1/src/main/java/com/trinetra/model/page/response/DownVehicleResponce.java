package com.trinetra.model.page.response;

import java.sql.Timestamp;

public interface DownVehicleResponce {
	
	public Integer getAlertId();

	public String getVltdName();

	public String getVehicleRegNo();

	public String getChassisNo();

	public String getDeviceSrNo();

	public String getDeviceIccid();

	public String getImeiNo();

	public String getPermitHolderName();

	public Timestamp getLastPollingTime();

	public String getLastEmergencyalerttime();

	public int getLastSpeed();

	public String getLastLocation();

	public String getStatus();

	public String getDownLat();

	public String getDownLon();

	public String getRtoName();

	public Boolean getIgnition();

}
