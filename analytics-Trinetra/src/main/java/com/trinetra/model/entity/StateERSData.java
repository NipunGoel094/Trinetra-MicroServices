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
@Table(name = "view_state_ers_data")
@Entity
@Getter
public class StateERSData {

	@Id
	@Column
	private int id;
	
	@Column
	private String alertType;
	
	@Column
	private String vehicleChassisNo;
	
	@Column
	private String deviceImei;
	
	@Column
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "Asia/Kolkata")
	private Date alertDate;
	
	@Column
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss", timezone = "Asia/Kolkata")
	private Date alertTime;
	
	@Column
	private String latitude;
	
	@Column(name = "eorw")
	private String eOrW;
	
	@Column
	private String longitude;
	
	@Column(name = "nors")
	private String nOrS;
	
	@Column
	private String speed;
	
	@Column
	private String rtoName;
	
	@Column
	private String stateName;
}
