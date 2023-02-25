package com.trinetra.model.wrapper;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MobileApiResponseWrapper {

	private String status;
    private Object content;
    private Timestamp timestamp;
    
    public MobileApiResponseWrapper(String status, Object content) {
    	this.status = status;
		this.content = content;
		timestamp = new Timestamp(System.currentTimeMillis());
    }
}
