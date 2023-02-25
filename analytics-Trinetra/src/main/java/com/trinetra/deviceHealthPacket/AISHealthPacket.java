package com.trinetra.deviceHealthPacket;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* 
* @author Sunita
* class used for the getting AIS health packet details 
* table ="ais_health_packet";
*/


@Entity
@Table(name="ais_health_packet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AISHealthPacket implements Serializable{

	@Id
	@Column(name="start_character")
	private String startCharacter;
	private String header;
	private String vendorId;
	private String firmwareVersion;
	private String imei;
	private float batteryPercentage;
	private Integer lowBatteryThresholdValue;
	private Integer memoryPercentage;
	private Integer dataUpdateRateWhenIgnitionOn;
	private Integer dataUpdateRateWhenIgnitionOff;
	private Integer digitalInput1;
	private Integer digitalInput2;
	private Integer digitalInput3;
	private Integer digitalInput4;
	private Integer digitalOutput1;
	private Integer digitalOutput2;
	private float analogInput1;
	private float analogIntput2;
	private String checksum;
	private String endCharacter;
	
}
