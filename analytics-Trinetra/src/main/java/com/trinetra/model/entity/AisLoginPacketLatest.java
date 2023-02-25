package com.trinetra.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "ais_login_packet_latest")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AisLoginPacketLatest {
	
	@Id
	@Column(name = "start_character")
	String startCharacter;
	
	@Column(name = "header")
	String header;
	
	@Column(name = "vehicle_reg_no")
	String vehicleRegNo;
	
	@Column(name = "imei")
	String imei;
	
	@Column(name = "firmware_version")
	String firmwareVersion;
	
	@Column(name = "protocol_version")
	String protocolVersion;
	
	@Column(name = "latitude")
	Double latitude;
	
	@Column(name = "latitude_dir")
	String latitudeDir;
	
	@Column(name = "longitude")
	Double longitude;
	
	@Column(name = "longitude_dir")
	String longitudeDir;
	
	@Column(name = "end_character")
	String endCharacter;
	
	@Column(name = "log_date")
	Date logDate;
	
	@Column(name = "recieved_date_time")
	Date receivedDateTime;
}
