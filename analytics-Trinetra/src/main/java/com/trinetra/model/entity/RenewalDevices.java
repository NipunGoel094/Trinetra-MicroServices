package com.trinetra.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Getter;

@Immutable
@Table(name = "renewal_devices")
@Entity
@Getter
public class RenewalDevices {

	@Id
	@Column
	private int id;
	
	@Column
	private String deviceUin;
	
	@Column
	private String deviceImei;
	
	@Column
	private String deviceIccid;
	
	@Column
	private String manufacturer;
	
	@Column
	private String dealerName;
	
	@Column
	private String rtoName;
	
	@Column
	private String permitHolderName;
	
	@Column
	private String primaryMobileNo;
	
	@Column
	private String pollingStatus;
	
	@Column
	private String vehicleRegNo;
	
	@Column
	private String vehicleChassisNo;
}
