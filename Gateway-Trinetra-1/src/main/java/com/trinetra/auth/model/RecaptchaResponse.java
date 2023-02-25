package com.trinetra.auth.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class RecaptchaResponse {
	
	private boolean success;
	private Timestamp challenge_ts;
	private String hostname; 
}
