package com.trinetra.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Getter;

@Immutable
@Table(name = "rto_response_view")
@Entity
@Getter
public class RtoResponseView {
	
	@Id
	@Column
	private Integer id;
	
	@Column
	private String noOfRto;
	
	@Column
	private Integer stateId;
	
	@Column
	private Integer gPinCodeId;
}
