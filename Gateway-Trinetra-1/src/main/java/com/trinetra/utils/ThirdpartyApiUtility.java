package com.trinetra.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.trinetra.exception.CustomException;
import com.trinetra.model.entity.SimSMS;
import com.trinetra.model.page.response.SensoriseApiResponse;
import com.trinetra.model.page.response.SimSmsResponse;
import com.trinetra.model.page.response.TaisysApiResponse;
import com.trinetra.model.request.SansoSmsDeliveryResquest;
import com.trinetra.model.response.SansoSmsDeliveryResponse;
//import com.trinetra.model.response.VltdSimVehicleResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ThirdpartyApiUtility {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private GenerateAccessKey generateAccessKey;

	public SensoriseApiResponse checkOrsacSIM(String baseUrl, String iccid, String cryptoLogicStub, String preSharedKey,
			String corelationId, int tokenValidity, int tokenTransactions) throws CustomException {
		String enpointUrl = baseUrl.concat("/checkOrsacSIM");
		try {
			String accessKey = generateAccessKey.getAccessKey(cryptoLogicStub, preSharedKey, corelationId);
			Map<String, String> requestMap = new HashMap<>();
			requestMap.put("iccid", iccid);
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set("relationId", corelationId);
			httpHeaders.set("cryptoLogic", cryptoLogicStub);
			httpHeaders.set("accessKey", accessKey);
			httpHeaders.set("apiToken", generateAPIToken(baseUrl, tokenValidity, tokenTransactions,
					corelationId, cryptoLogicStub, preSharedKey));
			HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(requestMap, httpHeaders);
			ResponseEntity<SensoriseApiResponse> responseEntity = restTemplate.exchange(enpointUrl, HttpMethod.POST,
					httpEntity, SensoriseApiResponse.class);
			log.info("HttpResponse Status from Sensorise checkOrsacSIM API: {}", responseEntity.getStatusCodeValue());
			return responseEntity.getBody();
		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
			log.error("HttpResponse Error Status from Sensorise checkOrsacSIM API: {}", e.getRawStatusCode());
			throw new CustomException("Exception occurred while consuming Sensorise API");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("Exception occurred while consuming SenseLCM API");
		}
	}

	@SuppressWarnings("rawtypes")
	public String generateAPIToken(String baseUrl, int tokenValidity, int tokenTransactions, String corelationId,
			String cryptoLogicStub, String preSharedKey) throws CustomException {

		String enpointUrl = baseUrl.concat("/generateAPIToken");
		try {
			String accessKey = generateAccessKey.getAccessKey(cryptoLogicStub, preSharedKey, corelationId);
			Map<String, Integer> requestMap = new HashMap<>();
			requestMap.put("tokenValidity", tokenValidity);
			requestMap.put("tokenTransactions", tokenTransactions);

			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set("relationId", corelationId);
			httpHeaders.set("cryptoLogic", cryptoLogicStub);
			httpHeaders.set("accessKey", accessKey);

			HttpEntity<Map<String, Integer>> httpEntity = new HttpEntity<>(requestMap, httpHeaders);
			ResponseEntity<Map> responseEntity = restTemplate.exchange(enpointUrl, HttpMethod.POST, httpEntity,
					Map.class);
			log.info("HttpResponse Status from Sensorise generateAPIToken API: {}",
					responseEntity.getStatusCodeValue());
			return String.valueOf(responseEntity.getBody().get("apiToken"));
		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
			log.error("HttpResponse Error Status from Sensorise generateAPIToken API: {}", e.getRawStatusCode());
			throw new CustomException("Exception occurred while consuming Sensorise");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("Exception occurred while consuming Sensorise");
		}
	}
	
	public TaisysApiResponse checkTaisysSimValidity(String baseUrl,String imei) throws CustomException {
		String enpointUrl = baseUrl.concat("/svimeid");
		try {
			Map<String, String> requestMap = new HashMap<>();
			requestMap.put("k1", imei);
			HttpHeaders httpHeaders = new HttpHeaders();
			//httpHeaders.set("Authorization", "Bearer ");
			HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(requestMap, httpHeaders);
			ResponseEntity<TaisysApiResponse> responseEntity = restTemplate.exchange(enpointUrl, HttpMethod.POST, httpEntity, TaisysApiResponse.class);
			log.info("HttpResponse Status from TaisysApiResponse checkTaisysSimValidity API: {}",responseEntity.getStatusCodeValue());
			return responseEntity.getBody();
		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
			log.error("HttpResponse Error Status from TaisysApiResponse checkTaisysSimValidity API: {}", e.getRawStatusCode());
			throw new CustomException("Exception occurred while consuming TaisysApiResponse checkTaisysSimValidity API");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("Exception occurred while consuming TaisysApiResponse checkTaisysSimValidity API");
		}
	}

	public SimSmsResponse checkOrsacSMS(String baseUrl,SimSMS sms,String cryptoLogicStub, String sharedKey,
			String relationId, int tokenValidity, int tokenTransactions) throws CustomException {
		String enpointUrl ="https://senselcm.sensorise.net/sense-lcm-services-prod/sendsms/v1/"; 
		try {
			String accessKey = generateAccessKey.getAccessKey(cryptoLogicStub, sharedKey, relationId);
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set("relationId", relationId);
			httpHeaders.set("cryptoLogic", cryptoLogicStub);
			httpHeaders.set("accessKey", accessKey);
			httpHeaders.set("apiToken", generateAPIToken(baseUrl, tokenValidity, tokenTransactions,
					relationId, cryptoLogicStub, sharedKey));
			
			System.out.println("Api Token"+generateAPIToken(baseUrl, tokenValidity, tokenTransactions,
					relationId, cryptoLogicStub, sharedKey));
			//need to change here 
			
			
			HttpHeaders httpHeader1=new HttpHeaders();
			httpHeader1.set("relationId", "RAPL_Test");
			httpHeader1.set("apiToken", generateAPIToken(baseUrl, tokenValidity, tokenTransactions,
					relationId, cryptoLogicStub, sharedKey));
			
			HttpEntity<SimSMS> entity = new HttpEntity<SimSMS>(sms,httpHeader1);
		     
			ResponseEntity<SimSmsResponse> responseEntity = restTemplate.exchange(enpointUrl, HttpMethod.POST,
			entity, SimSmsResponse.class);
			return responseEntity.getBody();
		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
			log.error("HttpResponse Error Status from Sensorise checkOrsacSMS API: {}", e.getRawStatusCode());
			throw new CustomException("Exception occurred while consuming SensoriseSMS API");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("Exception occurred while consuming SenseLCM API");
		}
	}

	public SansoSmsDeliveryResponse checkSmsDelevery(String baseUrl,SansoSmsDeliveryResquest sms,String cryptoLogicStub, String sharedKey,
			String relationId, int tokenValidity, int tokenTransactions) throws CustomException {
		String enpointUrl ="https://senselcm.sensorise.net/sense-lcm-services-prod/sms_status/v1"; 
		try {
			String accessKey = generateAccessKey.getAccessKey(cryptoLogicStub, sharedKey, relationId);
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set("relationId", relationId);
			httpHeaders.set("cryptoLogic", cryptoLogicStub);
			httpHeaders.set("accessKey", accessKey);
			httpHeaders.set("apiToken", generateAPIToken(baseUrl, tokenValidity, tokenTransactions,
					relationId, cryptoLogicStub, sharedKey));
			
			System.out.println("Api Token"+generateAPIToken(baseUrl, tokenValidity, tokenTransactions,
					relationId, cryptoLogicStub, sharedKey));
			//need to change here 
			
			
			HttpHeaders httpHeader1=new HttpHeaders();
			httpHeader1.set("relationId", "RAPL_Test");
			httpHeader1.set("apiToken", generateAPIToken(baseUrl, tokenValidity, tokenTransactions,
					relationId, cryptoLogicStub, sharedKey));
			
			HttpEntity<SansoSmsDeliveryResquest> entity = new HttpEntity<SansoSmsDeliveryResquest>(sms,httpHeader1);
		     
			ResponseEntity<SansoSmsDeliveryResponse> responseEntity = restTemplate.exchange(enpointUrl, HttpMethod.POST,
			entity, SansoSmsDeliveryResponse.class);
			return responseEntity.getBody();
		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
			log.error("HttpResponse Error Status from Sensorise checkOrsacSMS API: {}", e.getRawStatusCode());
			throw new CustomException("Exception occurred while consuming SensoriseSMSDelivery API");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("Exception occurred while consuming SensoriseSMSDelivery API");
		}
	}
		
}
