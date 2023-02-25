package com.trinetra.model.useraccess.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.trinetra.config.Auditable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "menu_access_master")
public class MenuAccessMaster extends Auditable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_access_master_generator")
	@SequenceGenerator(name="menu_access_master_generator", sequenceName = "public.menu_access_master_id_seq", allocationSize=1)
	@Column(name = "id", updatable = false, nullable = false, insertable = false)
	private long id;
	
	private String jsonValue;
	
	private int userTypeId;
	
}
