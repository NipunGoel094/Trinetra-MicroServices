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
@Table(name = "view_health_knowledge_check")
@Entity
@Getter
public class HealthKnowledgeCheck {

	@Id
	@Column
	private int id;
	
	@Column(name = "deviceimei")
	private String deviceImei;
	
	@Column
	private String header;
	
	@Column(name = "vendorid")
	private String vendorID;
	
	@Column(name = "firmwareversion")
	private String firmwareVersion;
	
	@Column
	private String location;
	
	@Column
	private String lat;
	
	@Column
	private String lon;
	
	@Column(name = "gpsstatus")
	private String gpsstatus;
	
	@Column(name = "smsdate")
	private String smsdate;
	
	@Column(name = "receiveddatetime")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
	private Date receivedDateTime;
	
	@Column(name = "localareacode")
	private String localAreaCode;
	
	@Column
	private String heading;
	
	@Column
	private String speed;
	
	@Column(name = "gsmsignalstrength")
	private String gsmsignalStrength;
	
	@Column(name = "mobilecountrycode")
	private String mobileCountryCode;
	
	@Column(name = "mobilenetworkcode")
	private String mobileNetworkCode;
	
	@Column(name = "ignitionstatus")
	private Boolean ignitionStatus;
}
