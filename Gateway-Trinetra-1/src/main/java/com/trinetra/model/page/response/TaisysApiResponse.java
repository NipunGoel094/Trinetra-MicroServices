package com.trinetra.model.page.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class TaisysApiResponse {
	
	private String apiResponseCode;

	private String apiResponseMsg;

	private int imeiEntityCount;

	private List<TaisysImeiEntity> imeiEntity;
}
