package com.trinetra.model.page.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RenewalDeviceDetailsResponse {
	public String Device_Sr_No;
	public String Device_IMEI_No;
	public String Device_ICCID_No;
	public String ESIM_Provider;
	public String VLTD_Manufacturer;
	public String Dealer_Name;
	public String RTO_Name;
	public String Vehicle_Reg_No;
	public String Vehicle_Chassis_No;
	public String Permit_Holder_Name;
	public String Permit_Holder_MobileNo;
	public String Renewal_Date;
	public String Next_Renewal_Date;
	public String Renewal_Status;
	public String Polling_Status;
}
