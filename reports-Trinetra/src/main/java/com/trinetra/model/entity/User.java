package com.trinetra.model.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trinetra.config.Auditable;
import com.trinetra.model.entity.master.Role;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_constraint_username1", columnNames = {"username"}),
                @UniqueConstraint(name = "unique_constraint_user_email1", columnNames = {"email_id"})
        }
)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User extends Auditable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username",nullable = false,length = 50)
    private String username;

    @Column(name = "firstname",length = 50)
    private String firstname;

    @Column(name = "lastname",length = 50)
    private String lastname;

    private String password;
    @Column(name = "email_id",length = 100)
    //@Column(name = "email_id",nullable = false,length = 100)
    private String email;
    @Column(name = "contact")
   // @Column(name = "contact",nullable = false)
//    @Phone
    private String mobileNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    

//    @Column(name="roles", columnDefinition = "varchar(50) default 'ROLE_USER'")
//    private String roles;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Column(name = "token_creation_date")
    private LocalDateTime tokenCreationDate;
    
    @Column(name = "user_type_id")
    private int userTypeId;
    
    @SuppressWarnings("rawtypes")
    @Transient
    private List<Map> menuAccessJson;
    
    
  

    public User(String username,String firstname,String lastname, String password, String email, String mobileNumber,Address address) {
        this.username = username;
        this.firstname=firstname;
        this.lastname= lastname;
        this.password = password;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.address = address;
    }
    public User(Long id,String username,String firstname,String lastname, String password, String email, String mobileNumber) {
        this.id = id;
        this.username = username;
        this.firstname=firstname;
        this.lastname= lastname;
        this.password = password;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public LocalDateTime getTokenCreationDate() {
        return tokenCreationDate;
    }

    public void setTokenCreationDate(LocalDateTime tokenCreationDate) {
        this.tokenCreationDate = tokenCreationDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
	public int getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getMenuAccessJson() {
		return menuAccessJson;
	}
	@SuppressWarnings("rawtypes")
	public void setMenuAccessJson(List<Map> menuAccessJson) {
		this.menuAccessJson = menuAccessJson;
	}
	
    
}