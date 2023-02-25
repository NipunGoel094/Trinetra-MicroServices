package com.trinetra.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.trinetra.config.Auditable;
import com.trinetra.model.entity.master.TypeMaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "oem_master")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class OemMaster extends Auditable<Long> {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oem_generator")
	@SequenceGenerator(name="oem_generator", sequenceName = "public.oem_master_id_seq", allocationSize=1)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	@Column(name = "name", nullable = false)
	private String name;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;

	@Column(name = "gstin_no",nullable = false)
	private String gstinNo;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
	private Person contactPerson;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
    @JoinColumn(name = "oem_type", referencedColumnName = "id")
	private TypeMaster oemType;
	
	private String oemAbbreviation;
	
	
	private String oemShortName;
	
	
	private String paymentTerm;
	
	
	private long vehicleType;


	private String oemCompanyName;
	

	private String vahanOemName;
	
	private String manufacturerCode;
	
	private String cinNo;
	
	@Column(name = "ais_140_approved")
	private boolean ais140Approved;
	
	private boolean smartMobilityMember;

}
