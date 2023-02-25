package com.trinetra.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private HttpStatus errorCode;
    private String errorMessage;
    private List<String> errors;


    public ExceptionResponse(HttpStatus status, String errorMessage, String error) {
        super();
        this.errorCode = status;
        this.errorMessage = errorMessage;
        errors = Arrays.asList(error);
    }

    public ExceptionResponse(HttpStatus status, String errorMessage) {
        super();
        this.errorCode = status;
        this.errorMessage = errorMessage;

    }

}
