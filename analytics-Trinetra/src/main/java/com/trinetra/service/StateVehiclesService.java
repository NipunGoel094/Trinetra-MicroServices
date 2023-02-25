package com.trinetra.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trinetra.model.entity.URLUtil;
import com.trinetra.model.page.request.MapDetailsByDistrictRequest;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Service
public class StateVehiclesService {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public MapDetailsByDistrictRequest getMapDetailsByDistrict(String district) {
		URLConnection myURLConnection = null;
		URL myURL = null;
		BufferedReader reader = null;
		String response = null;
		Pattern whitespace = Pattern.compile("\\s");
    	Matcher matcher = whitespace.matcher(district);
    	if (matcher.find()) {
    		district = matcher.replaceAll("%20");
    	}
		MapDetailsByDistrictRequest mapDetailsByDistrictRequest = new MapDetailsByDistrictRequest();
		String urlString = URLUtil.GET_MAP_DETAILS_BY_DISTRICT + "&CQL_FILTER=uppername=" + "'" + district + "'";
		try {
			// prepare connection
			myURL = new URL(urlString);
			myURLConnection = myURL.openConnection();
			myURLConnection.connect();
			reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
			if ((response = reader.readLine()) != null) {
				System.out.println(response);
				mapDetailsByDistrictRequest = objectMapper.readValue(response, MapDetailsByDistrictRequest.class);
				return mapDetailsByDistrictRequest;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapDetailsByDistrictRequest;
	}

	public MapDetailsByDistrictRequest getMapDetailsByStateName(String stateName) {
		URLConnection myURLConnection = null;
		URL myURL = null;
		BufferedReader reader = null;
		String response = null;
		Pattern whitespace = Pattern.compile("\\s");
    	Matcher matcher = whitespace.matcher(stateName);
    	if (matcher.find()) {
    		stateName = matcher.replaceAll("%20");
    	}
		MapDetailsByDistrictRequest mapDetailsByDistrictRequest = new MapDetailsByDistrictRequest();
		String urlString = URLUtil.GET_MAP_DETAILS_BY_STATENAME+ "&CQL_FILTER=name="+"'"+stateName+"'";
		try {
			// prepare connection
			myURL = new URL(urlString);
			myURLConnection = myURL.openConnection();
			myURLConnection.connect();
			reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
			if ((response = reader.readLine()) != null) {
				System.out.println(response);
				mapDetailsByDistrictRequest = objectMapper.readValue(response, MapDetailsByDistrictRequest.class);
				return mapDetailsByDistrictRequest;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapDetailsByDistrictRequest;
	}
	
}
