package com.trinetra.model.useraccess.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"createdBy","createdDate","updatedBy","updatedDate","active"})
@Data
public class TabMasterDto{

	private long id;
	
	private String item;
	
	private String route;
	
	private boolean hasAccess;
	
	private int level;
	
	private boolean expandable;
	
	private int sequenceNo;
	
	private List<MenuTabActionMapping> menuTabActionMapping = new ArrayList<>();
}