package com.trinetra.model.page.request;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class MapGeometry {

	private String type;
	private List<List<List<List<BigDecimal>>>> coordinates;
}
