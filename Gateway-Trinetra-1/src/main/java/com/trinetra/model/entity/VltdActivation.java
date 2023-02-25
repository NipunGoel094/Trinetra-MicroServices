package com.trinetra.model.entity;
/**
 * 
 * @author Sunita
 * mapping class for saving the vltd Activation data
 * table ="Vltd_activation";
 */

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.trinetra.config.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Vltd_activation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VltdActivation extends Auditable<Long>{	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int deviceId;
	private String vehicleRegNo;
	private String vehicleMake;
	private String engineNo;
	private String chassisNo;
	private String vehicleModel;
	private String vahanPermitholderName;
	private int dealerId;
	
	
	
	
	
}
