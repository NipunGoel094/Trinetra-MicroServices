package com.trinetra.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Immutable
@Table(name = "view_login_packet_response")
@Entity
@Getter
public class LoginPacketCheck {
	
	@Id
	@Column
	private int id;
	
	@Column(name = "deviceimei")
	private String deviceImei;
	
	@Column(name = "vehicleregno")
	private String vehicleRegNo;
	
	@Column(name = "datetime")
	private String dateTime;
	
	@Column(name = "lat")
	private BigDecimal lat;
	
	@Column(name = "lon")
	@JsonProperty("long")
	private BigDecimal lon;
	
}
