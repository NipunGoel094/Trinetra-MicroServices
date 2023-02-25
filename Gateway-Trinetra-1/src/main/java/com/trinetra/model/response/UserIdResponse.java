package com.trinetra.model.response;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.trinetra.model.entity.master.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIdResponse {

private long id;
private int userTypeId;
private String username;
@Column(name = "updated_by")
private Long updatedBy;
@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
@JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
)
private Set<Role> roles = new HashSet<>();
 @SuppressWarnings("rawtypes")
    @Transient
private List<Map> menuAccessJson;
 
private String message;
}
