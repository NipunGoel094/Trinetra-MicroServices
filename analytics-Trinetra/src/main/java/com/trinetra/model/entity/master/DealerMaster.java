package com.trinetra.model.entity.master;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trinetra.config.Auditable;
import com.trinetra.model.entity.Address;
import com.trinetra.model.entity.OemMaster;
import com.trinetra.model.entity.Person;
import com.trinetra.model.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="dealer_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DealerMaster  extends Auditable<Long>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dealer_generator")
	@SequenceGenerator(name="dealer_generator", sequenceName = "public.dealer_master_id_seq", allocationSize=1)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	@OneToOne
	@JoinColumn(name = "dealer_type_id", referencedColumnName = "id")
	private TypeMaster dealerTypeId;
	
	private String dealerName;
	
	private String dealerCode;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address addressId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "person_id", referencedColumnName = "id")
	private Person personId;
	
	private Timestamp systemJoiningDate;
	
	private Integer accountBalance;
	
	private String areaOfDealer;
	
	private boolean isDealingInTwoWheeler;
	
	private boolean isDealingInFourWheeler;
	
	private boolean isDealingInCommercial;
	
	private String chargingType;
	
	private Integer rtoLocationId;
	
	private String erpClientCode;
	
	private Integer openingBalance;
	
	private Integer fixingCharge;
	
	private String gstin;
	
	@OneToOne
	@JoinColumn(name = "oem_id", referencedColumnName = "id")
	private OemMaster oemTypeId;
	
	
	private Integer avgSales;
	
	private String remarks;
	
	private String billToStatusForInvoice;
	
	private String billToGstin;
	
	private String shipToGstin;
	
	private String businessType;
	
	private String gstImage;
	
	private boolean  dealerGstMatch;
	
	private boolean  dealerNameMatch;
	
	private boolean  dealerAddressMatch;
	
	private boolean  dealerStateMatch;
	
	private boolean  dealerReject;
	
	private Integer creditAmount;
	
	private Integer creditDays;
	
	private Date creditDate;
	
	private boolean  isExternal;
	
	private String typeOfDelivery;
	
	private boolean  isVahan;

	private Long userId;
}


