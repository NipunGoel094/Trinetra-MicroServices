package com.trinetra.model.wrapper;

import com.trinetra.auth.model.JwtResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.sql.Time;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class ResponseWrapper {

    private String status;
    private Object data;
    private Timestamp timestamp;


    public ResponseWrapper(String status, Object data) {
        this.status = status;
        this.data = data;
        timestamp = new Timestamp(System.currentTimeMillis());
    }


}
