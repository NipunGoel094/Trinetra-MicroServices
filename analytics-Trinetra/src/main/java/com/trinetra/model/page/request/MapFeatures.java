package com.trinetra.model.page.request;

import java.util.Map;

import lombok.Data;

@Data
public class MapFeatures {

	private String type;
	private String id;
	private MapGeometry geometry;
	private String geometry_name;
	private Map<String, Object> properties;
}
