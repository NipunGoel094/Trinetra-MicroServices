package com.trinetra.model.page.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class SimSmsResponse {

	private String apiResponseCode;
	private String apiResponseMsg;
	private String transactionId;
	private String msisdn;
}
