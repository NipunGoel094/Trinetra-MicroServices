package com.trinetra.model.entity;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trinetra.config.Auditable;
import com.trinetra.model.entity.Address;
import com.trinetra.validator.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.trinetra.constant.Constant.EMAIL_REGEX;

@Entity
@Table(name="person_master")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person extends Auditable<Long> {


	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "person_generator")
	@SequenceGenerator(name="person_generator", sequenceName = "public.person_master_id_seq", allocationSize=1)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	private long personType;
	private String personName;
//	@Phone
	private String primaryMobileNo;
//	@Phone
	private String secondaryMobileNo;

	@Email(regexp = EMAIL_REGEX,
			message = "Email address is not valid !!")
	@Size(max=100)
	private String emailId;


	private Date  dob;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "permanent_address_id", referencedColumnName = "id")
	private Address permanentAddress;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "temporary_address_id", referencedColumnName = "id")
	private Address temporaryAddress;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
	private Address shippingAddress;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "billing_address_id", referencedColumnName = "id")
	private Address billingAddress;

	private String remark;



}
