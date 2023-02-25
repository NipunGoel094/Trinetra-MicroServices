package com.trinetra.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseforSignIn {
  private String jwt;
   private String tokenType;
//   private Map<String,String> user;
   private UserIdResponse user;
}
