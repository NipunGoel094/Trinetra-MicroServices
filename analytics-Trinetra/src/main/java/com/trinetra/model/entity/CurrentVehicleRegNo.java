package com.trinetra.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Getter;

@Immutable
@Table(name = "current_vehicle_reg_no")
@Entity
@Getter
public class CurrentVehicleRegNo {
	
	@Id
	@Column
	private int id;
	
	@Column
	private String vehicleRegNo;
	
	@Column
	private String vltdManufacturerName;
	
	@Column
	private String rtoName;
	
	@Column
	private Boolean ignition;
}
