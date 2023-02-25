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
@Entity(name = "menu_tab_action_mapping")
public class MenuTabActionMapping extends Auditable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_tab_action_mapping_generator")
	@SequenceGenerator(name="menu_tab_action_mapping_generator", sequenceName = "public.menu_tab_action_mapping_id_seq", allocationSize=1)
	@Column(name = "id", updatable = false, nullable = false, insertable = false)
	private long id;
	
	private String item;
	
	private String route;
	
	private boolean hasAccess;
	
	private int level;
	
	private boolean expandable;
	
	private int sequenceNo;
	
}
