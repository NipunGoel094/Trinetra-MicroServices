package com.trinetra.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Immutable
@Table(name = "view_over_speed_alerts")
@Entity
@Getter
public class OverSpeedAlert {

	@Id
	@Column
	private int id;
	
	@Column
	@JsonProperty("vltdName")
	private String vltdManufacturerName;
	
	@Column
	private String dealerName;
	
	@Column
	private String permitHolderName;
	
	@Column
	private String rtoName;
	
	@Column
	private String deviceImei;
	
	@Column
	private String deviceIccid;
	
	@Column
	private String vehicleRegNo;
	
	@Column
	private String alertType;
	
	@Column
	private String dateTime;
	
	@Column
	private String startDateTime;
	
	@Column
	private String endDateTime;
	
	@Column
	private String speed;
	
	@Column
	private String ignitionStatus;
	
	@Column
	private String vehicleStatus;
	
	@Column
	private String gpsStatus;
	
	@Column
	private String overAlertLatLong;
	
	@Column
	private String alertLocation;
}
