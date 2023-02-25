package com.trinetra.model.request;

import com.trinetra.validator.Password;
import com.trinetra.validator.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {

    @NotBlank(message="password can't be blank !!")
    @Password
    private String password;
    
    private String token;

}
