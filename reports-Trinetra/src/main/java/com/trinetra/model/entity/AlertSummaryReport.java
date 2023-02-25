package com.trinetra.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Immutable
@Table(name = "view_alert_summary_report")
@Entity
@Getter
public class AlertSummaryReport {

	@Id
	@Column
	private int id;
	
	@Column
	@JsonProperty("vltdName")
	private String vltdManufacturerName;
	
	@Column
	private String dealerName;
	
	@Column
	private String personName;
	
	@Column
	private String rtoName;
	
	@Column
	private String deviceImei;
	
	@Column
	private String deviceIccid;
	
	@Column
	private String vehicleRegNo;
	
	@Column
	private Boolean ignition;
	
	@Column
	private Double speed;
	
	@Column
	private String alertType;
	
	@Column
	private String alertCount;
	
	@Column
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.S", timezone = "Asia/Kolkata")
	@JsonProperty("alertTime")
	private Date alertDateTime;
	
	@Column
	private String location;
	
	@Column
	private String alertLat;
	
	@Column
	private String alertLon;
}
