package com.trinetra.model.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Getter;

@Immutable
@Table(name = "view_live_vehicle")
@Entity
@Getter
public class LiveVehicle {

	@Id
	@Column
	private int id;
	
	@Column
	private String vehicleRegNo;
	
	@Column
	private String deviceImei;
	
	@Column
	private Double speed;
	
	@Column
	private String dateTime;
	
	@Column
	private String location;
	
	@Column
	private BigDecimal lat;
	
	@Column
	private BigDecimal lng;
	
	@Column
	private String vltdManufacturerName;
	
	@Column
	private String rtoName;
	
	@Column
	private Boolean ignition;
	
	@Column
	private Double direction;
}
