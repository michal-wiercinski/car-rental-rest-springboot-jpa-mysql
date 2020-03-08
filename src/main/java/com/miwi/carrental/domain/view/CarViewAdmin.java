package com.miwi.carrental.domain.view;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.context.annotation.Scope;

@Scope("request")
@Entity
@Table(name = "details_fleet_for_admin")
public class CarViewAdmin extends CarView {

  @Column(name = "income")
  private BigDecimal income;

  @Column(name = "current_mileage")
  private Integer currentMileage;

  @Column(name = "registration_number")
  private String registrationNumber;

  @Column(name = "avg_fuel_consumption")
  private Double fuelConsumption;

  public BigDecimal getIncome() {
    return income;
  }

  public void setIncome(BigDecimal income) {
    this.income = income;
  }

  public Integer getCurrentMileage() {
    return currentMileage;
  }

  public void setCurrentMileage(Integer currentMileage) {
    this.currentMileage = currentMileage;
  }

  public String getRegistrationNumber() {
    return registrationNumber;
  }

  public void setRegistrationNumber(String regNumber) {
    this.registrationNumber = regNumber;
  }

  public Double getFuelConsumption() {
    return fuelConsumption;
  }

  public void setFuelConsumption(Double fuelConsumption) {
    this.fuelConsumption = fuelConsumption;
  }
}