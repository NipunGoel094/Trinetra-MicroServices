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
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Immutable
@Table(name = "view_sms_acknowledgement_report")
@Entity
@Getter
public class SmsAcknowledgementReport {

	@Id
	@Column
	private int id;
	
	@Column
	@JsonProperty("vltdName")
	private String vltdManufacturerName;
	
	@Column
	private String deviceImei;
	
	@Column
	private String deviceIccid;
	
	@Column
	private String dealerName;
	
	@Column
	@JsonProperty("mobileNo")
	private String primaryMobileNo;
	
	@Column
	@JsonIgnore
	private String firmwareVersion;
	
	@Column
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Kolkata")
	@JsonProperty("recievedDataTime")
	private Date recievedDateTime;
	
	@Column
	@JsonProperty("status")
	private String gpsStatus;
	
	@Column
	private String location;
	
	@Column
	private String smsLat;
	
	@Column
	private String smsLon;
}
