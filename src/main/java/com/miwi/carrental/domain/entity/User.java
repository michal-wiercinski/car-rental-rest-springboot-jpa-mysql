package com.miwi.carrental.domain.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Table(name = "user")
@Entity
public class User implements Serializable {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "PK_user")
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "password")
  private String password;

  @Column(name = "email")
  private String email;

  @OneToOne
  @JoinColumn(name = "FK_user_details")
  private UserDetail userDetail;

  @OneToMany(mappedBy = "user")
  private List<Rental> rentals;

  @ManyToMany
  @JoinTable(name = "user_roles",
      joinColumns = @JoinColumn(name = "FK_user", referencedColumnName = "PK_user"),
      inverseJoinColumns = @JoinColumn(name = "FK_role", referencedColumnName = "PK_role"))
  private Collection<Role> roles;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Collection<Role> getRoles() {
    return roles;
  }

  public void setRoles(Collection<Role> roles) {
    this.roles = roles;
  }

  public UserDetail getUserDetail() {
    return userDetail;
  }

  public void setUserDetail(UserDetail userDetail) {
    this.userDetail = userDetail;
  }

  public List<Rental> getRentals() {
    return rentals;
  }

  public void setRentals(List<Rental> rentals) {
    this.rentals = rentals;
  }

}