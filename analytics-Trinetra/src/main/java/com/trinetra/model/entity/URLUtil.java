/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinetra.model.entity;

public class URLUtil {

//    public static final String API_URL = "http://sensetouchqa.sensorise.net/SenseLCM-Services";
	public static final String API_URL = "https://senselcm.sensorise.net/sense-lcm-services-prod";
	public static final String AUTH_ENDPOINT = "/generateAPIToken";
	public static final String BULK_IMEI_DATA_ENDPOINT = "/checkIMEIBulk/1.0";
	public static final String CCU_ENDPOINT = "/createCCO";
	public static final String CCU_STATUS_ENDPOINT = "/getCcoStatus/";
	public static final String CCU_BULK_STATUS_ENDPOINT ="/getBulkCcoStatus";
	public static final String GET_POI_ADDRESS="http://103.197.121.71:8080/reverse?format=json&addressdetails=0&namedetails=0&zoom=20";
	public static final String GET_MAP_DETAILS_BY_DISTRICT="http://103.76.214.246:9016/geoserver/trinetra_gis/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=trinetra_gis%3Adistrict_n&outputformat=application/json";
	public static final String GET_MAP_DETAILS_BY_STATENAME="http://103.76.214.246:9016/geoserver/trinetra_gis/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=trinetra_gis%3Astates&outputformat=application/json";

//	  https://paymelon.in/esim/taisys/svimeid
//    public static final String API_URL_TAISYS = "https://paymelon.in/esim/taisys";
//    public static final String API_URL_TAISYS = "http://103.210.73.96:8080/esim/taisys";
//    public static final String BULK_IMEI_DATA_ENDPOINT_TAISYS = "/svimeid";

}
