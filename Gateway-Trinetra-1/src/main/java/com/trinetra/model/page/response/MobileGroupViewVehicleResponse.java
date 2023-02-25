package com.trinetra.model.page.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface MobileGroupViewVehicleResponse {

public double getLat();
	
	public double getLon();
	
	public String getLocation();

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
	public Date getDateTime();
	
	public String getVehicleRegNo();
	
	public String getSpeed();
	
	public String getVltdManufacturerName();
	
	public String getRtoName();
	
	public Boolean getIgnition();
}
