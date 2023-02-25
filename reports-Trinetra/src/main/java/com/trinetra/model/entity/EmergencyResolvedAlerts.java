package com.trinetra.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Immutable
@Table(name = "view_emergency_resolved_alert_report")
@Entity
@Getter
public class EmergencyResolvedAlerts {
	
	@Id
	@Column
	private int id;
	
	@Column
	@JsonProperty("vltdName")
	private String vltdManufacturerName;
	
	@Column
	private String dealerName;
	
	@Column
	private Integer deviceId;
	
	@Column
	private String deviceImei;
	
	@Column
	private String deviceUin;
	
	@Column
	private String deviceIccid;
	
	@Column
	private String vehicleRegNo;
	
	@Column
	private String alertType;
	
	@Column
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Kolkata")
	private Date alertGenerationTime;
	
	@Column
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Kolkata")
	private Date resolvedTime;
	
	@Column
	private String resolverMobileNo;
	
	@Column
	private String location;
	
	@Column
	private String latitude;
	
	@Column
	private String longitude;
	
	@Column
	private String speed;
	
	@Column
	private String rtoName;
	
	@Column
	private Boolean ignition;
}
