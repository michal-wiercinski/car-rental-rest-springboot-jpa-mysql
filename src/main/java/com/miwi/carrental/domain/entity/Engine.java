package com.miwi.carrental.domain.entity;

import com.miwi.carrental.domain.enums.FuelType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

  @Column(name = "current_mileage")
  private Integer currentMileage;

  @Enumerated(EnumType.STRING)
  @Column(name = "fuel_type")
  private FuelType fuelType;

  @Column(name = "fuel_consumption")
  private Double fuelConsumption;


}
