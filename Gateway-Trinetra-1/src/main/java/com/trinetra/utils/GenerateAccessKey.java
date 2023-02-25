package com.trinetra.utils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.stereotype.Component;

import com.trinetra.model.entity.CryptoLogic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GenerateAccessKey {

	private CryptoLogic cryptoLogic;

	public GenerateAccessKey() {
		cryptoLogic = getCryptoLogic();
	}

	public CryptoLogic getCryptoLogic() {
		return new CryptoLogic();
	}

	private static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
		}

		public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
		}
	} };
	
	public String getAccessKey(String cryptoLogicStub, String preSharedKey, String corelationId) {
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		String accessKeyGen = "";
		try {
			accessKeyGen = cryptoLogic.applyCryptoLogic(cryptoLogicStub, preSharedKey, corelationId);
			log.info("accessKeyGen : " + accessKeyGen);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("faile to get access token ", e1);
		}
		
		return accessKeyGen;
	}
}
