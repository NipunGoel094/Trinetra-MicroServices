package com.trinetra.model.page.request;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MapDetailsByDistrictRequest {

	private String type;
	private List<MapFeatures> features;
	private Integer totalFeatures;
	private Integer numberMatched;
	private Integer numberReturned;
	private Date timeStamp;
	private MapCrs crs;
}
