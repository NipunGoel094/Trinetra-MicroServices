package com.trinetra.model.useraccess.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"createdBy","createdDate","updatedBy","updatedDate","active"})
@Data
public class MenuTabActionMapping {

	private long id;
	
	private String item;
	
	private String route;
	
	private boolean hasAccess;
	
	private int level;
	
	private boolean expandable;
	
	private int sequenceNo;
}
