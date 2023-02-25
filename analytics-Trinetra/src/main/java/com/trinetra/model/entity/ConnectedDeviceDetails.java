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
@Table(name = "view_connected_device_details")
@Entity
@Getter
public class ConnectedDeviceDetails {
	
	@Id
	@Column
	private int id;
	
	@Column
	private String vltdManufacturerName;
	
	@Column
	private String rtoName;
	
	@Column
	private String vehicleRegNo;
	
	@Column
	private String vehicleChassisNo;
	
	@Column
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
	private Date pollingTime;
	
	@Column
	private BigDecimal lat;
	
	@Column
	private BigDecimal lon;
	
	@Column
	private String location;
	
	@Column
	private String speed;
	
	@Column
	private String deviceImei;
	
	@Column
	private Boolean ignition;
}
