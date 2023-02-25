package com.trinetra.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Getter;

@Immutable
@Table(name = "district_master_view")
@Entity
@Getter
public class DistrictMasterView {

	@Id
	@Column
	private int id;
	
	@Column
	private String districtName;
	
	@Column
	private Integer stateId;
}
