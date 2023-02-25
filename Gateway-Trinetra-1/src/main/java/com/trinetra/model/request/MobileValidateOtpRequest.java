package com.trinetra.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MobileValidateOtpRequest {
	private String mobileNumber;
	private String otp;
	private String password;
}
