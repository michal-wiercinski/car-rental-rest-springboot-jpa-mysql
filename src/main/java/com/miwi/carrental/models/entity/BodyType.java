package com.miwi.carrental.models.entity;

import com.miwi.carrental.models.enums.EBodyType;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "body_type")
@Entity
public class BodyType implements Serializable {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "PK_body_type")
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "type_name")
  private EBodyType typeName;

  @Column(name = "number_of_seats")
  private Integer numberOfSeats;

  @Column(name = "number_of_doors")
  private Integer numberOfDoors;

  @Column(name = "fuel_tank_volume")
  private Integer fuelTankVolume;

  @Column(name = "volume_of_luggage")
  private Integer volumeOfLuggage;

  @OneToMany(mappedBy = "bodyType",
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
  private List<CarParameter> carParameters;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public EBodyType getTypeName() {
    return typeName;
  }

  public void setTypeName(EBodyType typeName) {
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

  public Integer getVolumeOfLuggage() {
    return volumeOfLuggage;
  }

  public void setVolumeOfLuggage(Integer maxVolumeOfLuggage) {
    this.volumeOfLuggage = maxVolumeOfLuggage;
  }
}