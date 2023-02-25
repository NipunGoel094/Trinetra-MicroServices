package com.trinetra.model.entity.master;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "type_master")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
@JsonInclude(Include.NON_NULL)
public class TypeMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "typemaster_generator")
	@SequenceGenerator(name="typemaster_generator", sequenceName = "public.type_master_id_seq", allocationSize=1)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name = "ownership")
	private String ownership;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "is_active")
	private boolean isActive;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "updated_date")
	private Timestamp updatedDate;
	
	@Column(name = "updated_by")
	private Long updatedBy;
	
	
	
	
		
	
}
