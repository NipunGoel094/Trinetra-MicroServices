package com.trinetra.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleResponse {
    private List<String> rolesAssigned;
    private List<String> roleNotFound;
    private List<String> alreadyAssigned;
}
