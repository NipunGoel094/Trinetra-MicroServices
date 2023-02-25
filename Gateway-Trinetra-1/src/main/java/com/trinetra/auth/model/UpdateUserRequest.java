package com.trinetra.auth.model;


import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.trinetra.model.entity.Address;
import com.trinetra.model.entity.mapping.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @NotBlank(message = "username can't be blank !!")
    @Size(min= 2,max=50,message = "username should be between 4-50 character !!")
    private String username;

    @NotBlank(message = "username can't be blank !!")
    @Size(min= 2,max=50,message = "firstname should be between 4-50 character !!")
    private String firstname;

    private String lastname;

    @NotBlank(message = "email can't be blank !!")
    @Email(regexp = "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])",
            message = "Email address is not valid !!"
    )
    @Size(max=100)
    private String email;

//    @Phone
    private String mobileNumber;

    //@NotNull(message="addresses attributes are required")
    @Valid
    private Address address;
    
    private List<String> roles;
}

//@Email(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])",
//@Email(regexp = "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])",