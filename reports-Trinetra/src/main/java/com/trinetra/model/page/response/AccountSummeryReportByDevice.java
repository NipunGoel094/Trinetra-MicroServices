package com.trinetra.model.page.response;

import lombok.Data;

@Data
public class AccountSummeryReportByDevice {
	
	private String vltdName;
	private String dealerName;
	private String personName;
	private String vehicleRegNo;
	private String deviceImei;
	private String deviceIccid;
	private String vehicleType;
	private String amount;
	private String tranactionDate;
	private String rechargeDate;
	
}
