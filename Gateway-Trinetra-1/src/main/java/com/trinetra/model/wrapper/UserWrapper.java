package com.trinetra.model.wrapper;

import com.trinetra.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWrapper {

   private List<User> users;
   private List<String> errors;
   private Map<String,List<String>> fieldErrors;
   private Map<String, Integer> metadata;


}
