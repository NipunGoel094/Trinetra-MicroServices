package com.trinetra.model.page.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class SensoriseApiResponse {

	private String apiResponseCode;
	
	private String apiResponseMsg;
	
	private String iccid;
	
	private String cardState;
	
	private String cardStatus;
	
	private String activateOn;
	
	private String expiredOn;
	
	private double dataUsage;
	
	private String dataUsageDate;
	
	private String primaryTSP;
	
	private String primaryMSISDN;
	
	private String primaryStatus;
	
	private String fallbackTSP;
	
	private String fallbackMSISDN;
	
	private String fallbackStatus;
}