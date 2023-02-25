package com.trinetra.model.page.response;

import java.math.BigDecimal;
import java.util.Date;

public interface ConnectedDevicesDetailsResponse {
	public String getVehicleRegNo();
	public String getVehicleChassisNo();
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
