package com.miwi.carrental.domain.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;


@Data
@Table(name = "location")
@Entity
public class Location implements Serializable {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "PK_location")
  private Long id;

  @Column(name = "location_name")
  private String name;

  @OneToOne
  @JoinColumn(name = "FK_address")
  private Address address;

  @OneToMany(mappedBy = "location",
      cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private List<Car> vehicles;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Car> getVehicles() {
    return vehicles;
  }

  public void setVehicles(List<Car> vehicles) {
    this.vehicles = vehicles;
  }
}