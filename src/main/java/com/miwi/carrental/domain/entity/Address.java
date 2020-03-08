package com.miwi.carrental.domain.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Table(name = "address")
@Entity
public class Address {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "PK_address")
  private Long id;

  @Column(name = "city")
  private String city;

  @Column(name = "street")
  private String street;

  @Column(name = "house_number")
  private String houseNumber;

  @Column(name = "zip_code")
  private String zipCode;

  @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
  private List<UserDetail> users;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getHouseNumber() {
    return houseNumber;
  }

  public void setHouseNumber(String houseNumber) {
    this.houseNumber = houseNumber;
  }

  public List<UserDetail> getUsers() {
    return users;
  }

  public void setUsers(List<UserDetail> customers) {
    this.users = customers;
  }
}