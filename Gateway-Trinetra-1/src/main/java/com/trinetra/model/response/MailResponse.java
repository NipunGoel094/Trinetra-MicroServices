package com.trinetra.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailResponse {
    private String from;
    private String to;
    private String token;
    private String message;
    private String status;


}
