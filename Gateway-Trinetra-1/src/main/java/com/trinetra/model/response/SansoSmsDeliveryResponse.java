package com.trinetra.model.response;
/**
 * 
 * @author Sunita 
 * mapping class used for the sensorise SMSDelivery Response data
 */
import lombok.Data;

@Data
public class SansoSmsDeliveryResponse {
	
	private String apiResponseCode;
	private String apiResponseMsg;
	private String transactionId;
	private String msisdn;
	private String status;

}
