package com.trinetra.model.entity.mapping;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name="users_roles")
public class UserRole {
    @Id
    private long user_id;
    private long role_id;
}
