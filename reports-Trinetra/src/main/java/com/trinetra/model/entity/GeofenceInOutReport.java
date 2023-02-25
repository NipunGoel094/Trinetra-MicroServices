package com.trinetra.model.entity;

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
@Table(name = "view_geofence_in_out_report")
@Entity
@Getter
public class GeofenceInOutReport {
	
	@Id
	@Column
	private int id;
	
	@Column
	@JsonProperty("vltdName")
	private String vltdManufacturerName;
	
	@Column
	private String dealerName;
	
	@Column
	@JsonProperty("permitHolderName")
	private String personName;
	
	@Column
	private String deviceImei;
	
	@Column
	private String deviceIccid;
	
	@Column
	private String vehicleRegNo;
	
	@Column
	private String alertDate;
	
	@Column
	private String inLat;
	
	@Column
	private String inLon;
	
	@Column
	private String outLat;
	
	@Column
	private String outLon;
	
	@Column
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.S", timezone = "Asia/Kolkata")
	private Date inTime;
	
	@Column
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.S", timezone = "Asia/Kolkata")
	private Date outTime;
	
	@Column
	private String poiName;
	
	@Column
	private String inDistance;
	
	@Column
	private String outDistance;
	
	@Column
	private String status;
	
}
