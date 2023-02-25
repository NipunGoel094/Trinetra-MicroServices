package com.trinetra.model.page.response;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface MobileLiveEmergencyResponse {


    public String getVltdManufacturerName();
    public String getDeviceSerialNo();
    public String getDeviceImeiNo();
    public String getDeviceIccidNo();
    public String getVehicleRegNo();
    public String getVehicleChassisNo();
    public String getPermitHolderName();
    public Boolean getPanicAlert();
    public Double getSpeed();
    public BigDecimal getLat();
    public BigDecimal getLon();
    public String getLocation();
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
    public Date getPollingTime();
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
    public Date getAlertGenerationTime();
	public String getDealerName();
    public String getRtoName();
	public Boolean getIgnition();
}
