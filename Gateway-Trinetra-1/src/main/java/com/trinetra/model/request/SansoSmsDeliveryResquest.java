package com.trinetra.model.request;
/**
 * 
 * @author Sunita 
 * mapping class used for the sensorise SMSDeliveryStatus Request data
 */
import lombok.Data;

@Data
public class SansoSmsDeliveryResquest {

	private String msisdn;
	private String transactionId;
}
