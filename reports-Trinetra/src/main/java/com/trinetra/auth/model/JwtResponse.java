package com.trinetra.auth.model;


import com.trinetra.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
   private String jwt;
   private String tokenType;
//   private Map<String,String> user;
   private User user;
}
