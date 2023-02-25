package com.trinetra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
//import com.trinetra.model.entity.master.CityMaster;
//import com.trinetra.model.entity.master.StateMaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Data
@Entity(name = "pincode_master")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class Pincode {
    @Id
    private int id;
    private String pincode;
    private Integer stateId;
    private Integer cityId;
    
    /*
    @OneToMany
    @JoinColumn(name = "state_id")
    private Set<StateMaster> stateId;
    
    @OneToMany
    @JoinColumn(name = "city_id")
    private Set<CityMaster> cityId;
    */
    //@OneToMany(mappedBy = "pincodeId")
    //private Address address;

}
