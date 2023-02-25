package com.trinetra.model.entity.master;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.trinetra.config.Auditable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="state_master")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateMaster extends Auditable<Integer>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private int id;
	private String name;
	
//	@OneToMany(mappedBy = "stateId")
//	private CityMaster city;
}
