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
@Table(name = "group_view_vehicle")
@Entity
@Getter
public class GroupViewVehicle {

	@Id
	@Column
	private int id;
	
	@Column
	private BigDecimal lat;
	
	@Column
	private BigDecimal lon;
	
	@Column
	private String location;
	
	@Column
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Kolkata")
	private Date dateTime;
	
	@Column
	private String vehicleRegNo;
	
	@Column
	private Double speed;
	
	@Column
	private Integer stateId;
	
	@Column
	private Integer rtoLocationId;
}
