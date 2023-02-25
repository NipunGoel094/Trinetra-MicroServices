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
@Table(name = "view_health_check")
@Entity
@Getter
public class HealthCheck {
	
	@Id
	@Column
	private int id;

	@Column
	private String startCharacter;
	
	@Column
	private String headerValue;
	
	@Column
	private String vendorId;
	
	@Column
	private String firmwareVersion; 
	
	@Column
	private String deviceImei; 
	
	@Column
	private Float batteryPercentage; 
	
	@Column
	private Integer lowBatteryThresholdValue;
	
	@Column
	private Integer memoryPercentage;
	
	@Column
	private Integer dataUpdateRateWhenIgnitionOn;
	
	@Column
	private Integer dataUpdateRateWhenIgnitionOff;
	
	@Column
	private Float digitalInput1; 
	
	@Column
	private Float digitalOutput1;
	
	@Column
	private Float analogInput1;
	
	@Column(name = "analog_input2")
	private Float analogIntput2;
	
	@Column
	private String checksum;
	
	@Column
	private String endCharacter;
	
	@Column(name = "log_date")
	private String logdate;
	
	@Column(name = "recieved_date_time")
	private Date recieveddatetime;
}
