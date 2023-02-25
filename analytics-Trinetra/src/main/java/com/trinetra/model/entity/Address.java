package com.trinetra.model.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trinetra.config.Auditable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "address_master")
public class Address  extends Auditable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_generator")
	@SequenceGenerator(name="address_generator", sequenceName = "public.address_master_id_seq", allocationSize=1)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
    
    
    @NotNull(message = "address type can't be blank !!")
    private long addressType;

//    @NotBlank(message = "address1 type can't be blank !!")
    private String address1;

//    @NotBlank(message = "address1 type can't be blank !!")
    private String address2;

    
    @Column(name = "pincode_id")
    private int pincodeId;
    
  /*  
    @OneToMany
    @JoinColumn(name = "pincode_id")
    private Set<Pincode> pincodeId;
    */
//    @OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "pincode_id", referencedColumnName = "id")
//	private Address pincodeId;

    private boolean isActive = true;

    @Transient
    private String pincode;
    @Transient
    private String city;
    @Transient
    private String state;

    public Address(long addressType, String address1, String address2,
    		Integer pincodeId
    		//Set<Pincode> pincodeId
    		) {
        this.addressType = addressType;
        this.address1 = address1;
        this.address2 = address2;
        this.pincodeId = pincodeId;
     //   this.pincodeId = pincodeId;
    }

}
