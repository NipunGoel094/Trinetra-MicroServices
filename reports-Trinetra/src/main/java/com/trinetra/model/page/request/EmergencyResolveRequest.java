package com.trinetra.model.page.request;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyResolveRequest {
	
	Integer deviceId;
	
	String alertType;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
	Date alertGenerationTime;
	
	Date resolvedTime = new Date();
	
	String resolverMobileNo;
	
	String location;
	
	BigDecimal latitude;
	
	BigDecimal longitude;
	
	String action;
	
	String remarks;
}
