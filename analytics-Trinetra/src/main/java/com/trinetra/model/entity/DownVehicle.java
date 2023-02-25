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
@Table(name = "view_down_vehicle")
@Entity
@Getter
public class DownVehicle {

	@Id
	@Column
	private int id;
	
	@Column
	private Integer alertId;
	
	@Column
	private Integer oemId;
	
	@Column(name = "vltd_manufacturer_name")
	private String vltdName;
	
	@Column
	private String vehicleRegNo;
	
	@Column(name = "vehicle_chassis_no")
	private String chassisNo;
	
	@Column
	private Integer deviceId;
	
	@Column
	private String deviceSrNo;
	
	@Column
	private String deviceIccid;
	
	@Column(name = "device_imei")
	private String imeiNo;
	
	@Column
	private Integer personId;
	
	@Column
	private String permitHolderName;
	
	@Column
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
	private Date lastPollingTime;
	
	@Column(name = "last_emergency_alert_time")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
	private Date lastEmergencyalerttime;
	
	@Column
	private Integer lastSpeed;
	
	@Column
	private String lastLocation;
	
	@Column(name = "gps_status")
	private String status;
	
	@Column
	private String downLat;
	
	@Column
	private String downLon;
	
	@Column
	private Boolean ignition;
	
	@Column
	private Integer rtoId;
	
	@Column
	private String rtoName;
}
