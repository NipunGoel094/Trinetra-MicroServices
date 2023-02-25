package com.trinetra.model.page.request;

import java.util.Map;

import lombok.Data;

@Data
public class MapCrs {

	private String type;
	private Map<String, String> properties;
}
