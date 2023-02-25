package com.trinetra.model.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="sanso_otp")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SansoOtp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String otp;
	private String mobileNo;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	private String sms;
}
