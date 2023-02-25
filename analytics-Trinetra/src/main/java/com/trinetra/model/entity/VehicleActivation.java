package com.trinetra.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Getter;

@Immutable
@Table(name = "vehicle_activation")
@Entity
@Getter
public class VehicleActivation {
	
	@Id
	@Column
	private int id;
	
	@Column
	private Integer deviceId;
	
	@Column
	private Integer vehicleId;
	
	@Column
	private Integer oemId;
	
	@Column
	private Integer vehicleTypeId;
	
	@Column
	private Integer customerId;
	
	@Column
	private Integer vdmId;
	
	@Column
	private String deviceImei;
	
	@Column
	private String deviceIccid;
	
	@Column
	private String deviceSrNo;
	
	@Column
	private String networkProvider1;
	
	@Column
	private String vehicleRegNo;
	
	@Column
	private String vehicleChassisNo;
	
	@Column
	private String rtoName;
	
	@Column
	private String permitHolderName;
	
	@Column
	private String manufacturerName;
	
	@Column
	private String dealerName;
	
	@Column
	private String deviceStatus;
	
	@Column
	private String vehicleStatus;
	
	@Column
	private String primaryMobileNo;
}
