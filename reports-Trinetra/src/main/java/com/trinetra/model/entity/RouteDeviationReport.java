package com.trinetra.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Immutable
@Table(name = "view_route_deviation_report")
@Entity
@Getter
public class RouteDeviationReport {
	
	@Id
	@Column
	@JsonIgnore
	private int id;
	
	@Column
	@JsonProperty("vltdName")
	private String vltdManufacturerName;
	
	@Column
	private String vehicleRegNo;
	
	@Column
	private String routeName;
	
	@Column
	private String rtoName;
	
	@Column
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.S", timezone = "Asia/Kolkata")
	private Date startTime;
	
	@Column
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.S", timezone = "Asia/Kolkata")
	private Date endTime;
}
