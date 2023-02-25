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
@Table(name = "view_live_emergency")
@Entity
@Getter
public class LiveEmergency {
	
	@Id
	@Column
	private int id;
	
	@Column
	private String vltdManufacturerName;
	
	@Column(name="device_sr_no")
	private String deviceSerialNo;
	
	@Column(name="device_imei")
	private String deviceImeiNo;
	
	@Column(name="device_iccid")
	private String deviceIccidNo;
	
	@Column
	private String vehicleRegNo;
	
	@Column
	private String vehicleChassisNo;
	
	@Column
	private String permitHolderName;
	
	@Column
	private Boolean panicAlert;
	
	@Column
	private Double speed;
	
	@Column
	private BigDecimal alertLat;
	
	@Column
	private BigDecimal alertLon;
	
	@Column
	private String location;
	
	@Column
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
	private Date alertGenerationTime;
	
	@Column
	private String rtoName;
	
	@Column
	private Boolean ignition;
	
	@Column
	private String portNo;
	
	@Column
	private String nmr;
	
	@Column(name="network_operator_ip")
	private String networkOperatorIP;
	
}
