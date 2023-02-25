package com.trinetra.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Getter;

@Immutable
@Table(name = "state_master_view")
@Entity
@Getter
public class StateMasterView {

	@Id
	@Column
	private int id;
	
	@Column
	private String name;
}
