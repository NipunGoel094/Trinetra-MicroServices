package com.trinetra.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {


    @NotBlank(message="username is blank !!")
    private String username;
    @NotBlank(message="password is blank !!")
    private String password;
    @NotBlank(message = "recaptcha is blank !!")
    private String recaptchaResponse;
}
