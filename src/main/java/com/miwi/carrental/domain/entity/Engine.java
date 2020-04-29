package com.miwi.carrental.domain.entity;

import com.miwi.carrental.domain.enums.FuelType;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "engine")
@Entity
public class Engine {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PK_engine")
  private Long id;

  @Column(name = "power")
  private Integer power;

  @Column(name = "capacity")
  private Integer capacity;

  @Enumerated(EnumType.STRING)
  @Column(name = "fuel_type")
  private FuelType fuelType;

  @Column(name = "fuel_consumption")
  private Double fuelConsumption;

  @OneToMany(mappedBy = "engine")
  private Collection<CarParameter> carParameters;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getPower() {
    return power;
  }

  public void setPower(Integer power) {
    this.power = power;
  }

  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  public FuelType getFuelType() {
    return fuelType;
  }

  public void setFuelType(FuelType fuelType) {
    this.fuelType = fuelType;
  }

  public Double getFuelConsumption() {
    return fuelConsumption;
  }

  public void setFuelConsumption(Double fuelConsumption) {
    this.fuelConsumption = fuelConsumption;
  }

  public Collection<CarParameter> getCarParameters() {
    return carParameters;
  }

  public void setCarParameters(
      Collection<CarParameter> carParameters) {
    this.carParameters = carParameters;
  }
}
