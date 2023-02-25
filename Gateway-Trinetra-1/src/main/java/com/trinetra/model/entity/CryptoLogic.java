package com.trinetra.model.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class CryptoLogic implements Serializable {

	private static final String CHARSET_NAME = "UTF-8";
	private static final String HMAC_SHA512 = "HmacSHA512";

	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	public String applyCryptoLogic(String logic, String preSharedKey, String relationId) {

		try {
			if (logic.equals("DEFAULT")) {

				return defaultLogic(preSharedKey, relationId);

			} else if (logic.equals("SWAP")) {
				
				return swapLogic(preSharedKey, relationId);
				
			}

		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	private static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars);
	}

	private String defaultLogic(String preSharedKey, String relationId)
			throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

		byte[] byteKey = preSharedKey.getBytes(CHARSET_NAME);
		Mac sha512_HMAC = Mac.getInstance(HMAC_SHA512);
		SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA512);
		sha512_HMAC.init(keySpec);
		String date = currenDate();
		String data = date + relationId;
		// System.out.println(data);
		byte[] mac_data = sha512_HMAC.doFinal(data.getBytes(CHARSET_NAME));
		String result = bytesToHex(mac_data);

		return result;
	}

	private String swapLogic(String preSharedKey, String relationId)
			throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

		byte[] byteKey = preSharedKey.getBytes(CHARSET_NAME);
		Mac sha512_HMAC = Mac.getInstance(HMAC_SHA512);
		SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA512);
		sha512_HMAC.init(keySpec);
		// data = Concatenation of relationId and Current Date (YYYYMMDD);
		String date = currenDate();
		String data = relationId + date;
		// System.out.println(data);
		byte[] mac_data = sha512_HMAC.doFinal(data.getBytes(CHARSET_NAME));
		String result = bytesToHex(mac_data);

		return result;

	}

	private String currenDate() {
		LocalDate date=LocalDate.now();
		//2021-12-30
		String currentDate=date.toString();
		String currentDateNow="";
		String currentDate1[]=currentDate.split("-");
		for(int i=0;i<currentDate1.length;i++) {
			currentDateNow=currentDateNow+currentDate1[i];
		}
		System.out.println("Hi::"+currentDateNow);
		//SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		//String date1 = simpleDateFormat.format(new Date());
		return currentDateNow;
	}

}
