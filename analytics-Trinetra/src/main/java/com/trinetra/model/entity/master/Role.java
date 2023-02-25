package com.trinetra.model.entity.master;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="role_master")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;

//    @ManyToMany(targetEntity = User.class, mappedBy = "roles", cascade = CascadeType.ALL)
//    private List<User> users;

    public Role(String name) {
        this.name = name;
    }



}
