package com.trinetra.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Immutable
@Table(name = "view_emergency_alert")
@Entity
@Getter
public class EmergencyAlertsReport {

	@Id
	@Column
	private int id;
	
	@Column
	@JsonProperty("vltdName")
	private String vltdManufacturerName;
	
	@Column
	private String dealerName;
	
	@Column
	@JsonProperty("permitHolderDetails")
	private String personName;
	
	@Column
	@JsonProperty("rto")
	private String rtoName;
	
	@Column
	private String deviceImei;
	
	@Column
	private Integer deviceId;
	
	@Column
	private String deviceUin;
	
	@Column
	private String deviceIccid;
	
	@Column
	private String vehicleRegNo;
	
	@Column
	private String alertType;
	
	@Column
	private String alertSpeed;
	
	@Column
	private String messageType;
	
	@Column
	private String alertDateTime;
	
	@Column
	private String gpsStatus;
	
	@Column
	private String location;
	
	@Column
	private String emergencyLoc;
	
	@Column
	private Boolean ignition;
}
