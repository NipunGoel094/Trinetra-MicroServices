package com.trinetra.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.trinetra.auth.model.RecaptchaResponse;

@PropertySource("classpath:application-dev.properties")
@Service
public class CaptchaService {
	
	@Autowired
	private RestTemplate restTemplate;

    @Value("${google.recaptcha.secret.key}")
    public String recaptchaSecret;
    @Value("${google.recaptcha.verify.url}")
    public String recaptchaVerifyUrl;

    public boolean verify(String response) {
        MultiValueMap<String, String> param= new LinkedMultiValueMap<>();
        param.add("secret", recaptchaSecret);
        param.add("response", response);
        
        RecaptchaResponse recaptchaResponse = null;
        
        try {
            recaptchaResponse = this.restTemplate.postForObject(recaptchaVerifyUrl, param, RecaptchaResponse.class);
            
            System.out.println("captcha response: " +recaptchaResponse);
        }catch(RestClientException e){
            System.out.print(e.getMessage());
        }
       if(recaptchaResponse.isSuccess()){
            return true;
       }
            return false;
    }
}
