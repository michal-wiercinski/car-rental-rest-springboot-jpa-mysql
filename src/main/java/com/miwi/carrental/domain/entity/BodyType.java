package com.miwi.carrental.domain.entity;

import java.io.Serializable;
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
@Table(name = "body_type")
@Entity
public class BodyType implements Serializable {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "PK_body_type")
  private Long id;

  @Column(name = "type_name")
  private String typeName;

  @Column(name = "number_of_seats")
  private Integer numberOfSeats;

  @Column(name = "number_of_doors")
  private Integer numberOfDoors;

  @Column(name = "fuel_tank_volume")
  private Integer fuelTankVolume;

  @Column(name = "min_volume_of_luggage")
  private Integer minVolumeOfLuggage;

  @Column(name = "max_volume_of_luggage")
  private Integer maxVolumeOfLuggage;

  @OneToMany(mappedBy = "bodyType",
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
  private List<CarParameter> carParameters;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public List<CarParameter> getCarParameters() {
    return carParameters;
  }

  public void setCarParameters(
      List<CarParameter> carParameters) {
    this.carParameters = carParameters;
  }

  public Integer getNumberOfSeats() {
    return numberOfSeats;
  }

  public void setNumberOfSeats(Integer numberOfSeats) {
    this.numberOfSeats = numberOfSeats;
  }

  public Integer getNumberOfDoors() {
    return numberOfDoors;
  }

  public void setNumberOfDoors(Integer numberOfDoors) {
    this.numberOfDoors = numberOfDoors;
  }

  public Integer getFuelTankVolume() {
    return fuelTankVolume;
  }

  public void setFuelTankVolume(Integer fuelTankVolume) {
    this.fuelTankVolume = fuelTankVolume;
  }

  public Integer getMinVolumeOfLuggage() {
    return minVolumeOfLuggage;
  }

  public void setMinVolumeOfLuggage(Integer minVolumeOfLuggage) {
    this.minVolumeOfLuggage = minVolumeOfLuggage;
  }

  public Integer getMaxVolumeOfLuggage() {
    return maxVolumeOfLuggage;
  }

  public void setMaxVolumeOfLuggage(Integer maxVolumeOfLuggage) {
    this.maxVolumeOfLuggage = maxVolumeOfLuggage;
  }
}