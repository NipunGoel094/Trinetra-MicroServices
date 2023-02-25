package com.trinetra.model.useraccess.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"createdBy","createdDate","updatedBy","updatedDate","active"})
@Data
public class UserAccessDTO {

	private List<String> hasAccessList;
	private String userType ;
	
}
