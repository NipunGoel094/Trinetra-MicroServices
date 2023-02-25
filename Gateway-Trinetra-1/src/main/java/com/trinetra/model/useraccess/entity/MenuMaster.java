package com.trinetra.model.useraccess.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.trinetra.config.Auditable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "menu_master")
public class MenuMaster extends Auditable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_master_generator")
	@SequenceGenerator(name="menu_master_generator", sequenceName = "public.menu_master_id_seq", allocationSize=1)
	@Column(name = "id", updatable = false, nullable = false, insertable = false)
	private long id;
	
	private String item;
	
	private String route;
	
	private boolean hasAccess;
	
	private int userTypeId;
	
	private int level;
	
	private boolean expandable;
	
	private int sequenceNo;
	
	@OneToMany(targetEntity = ChildMenuMaster.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "menu_id", referencedColumnName = "id", nullable = false)
	private List<ChildMenuMaster> childMenuMaster = new ArrayList<>();
	
}
