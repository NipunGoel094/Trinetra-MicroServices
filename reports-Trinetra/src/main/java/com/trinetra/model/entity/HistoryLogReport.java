package com.trinetra.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Immutable
@Table(name = "view_history_log_report")
@Entity
@Getter
public class HistoryLogReport {

	@Id
	@Column
	private int id;
	
	@Column
	private String vltdManufacturerName;
	
	@Column
	private String dealerName;
	
	@Column
	private String permitHolderName;
	
	@Column
	private String rtoName;
	
	@Column
	private String deviceUin;
	
	@Column
	private String deviceImei;
	
	@Column
	private String deviceIccid;
	
	@Column
	private String vehicleRegNo;
	
	@Column
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Kolkata")
	private Date pollingDateTime;
	
	@Column
	private Double speed;
	
	@Column
	private Boolean ignitionStatus;
	
	@Column
	private String vehicleStatus;
	
	@Column
	private String gpsStatus;
	
	@Column
	private BigDecimal eventLat;
	
	@Column
	private BigDecimal eventLong;
	
	@Column
	private String eventLocation;
	
	@Column
	private String alert;
}
