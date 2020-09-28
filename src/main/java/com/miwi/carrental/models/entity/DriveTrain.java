package com.miwi.carrental.models.entity;

import com.miwi.carrental.models.enums.EGearboxType;
import com.miwi.carrental.models.enums.EWheelDrive;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "drive_train")
@Entity
public class DriveTrain {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PK_drive_train")
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "wheel_drive")
  private EWheelDrive wheelDrive;

  @Enumerated(EnumType.STRING)
  @Column(name = "gearbox_type")
  private EGearboxType gearboxType;

  @Column(name = "number_of_gears")
  private Integer numberOfGears;

  @OneToMany(mappedBy = "driveTrain")
  private Collection<CarParameter> carParameters;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public EWheelDrive getWheelDrive() {
    return wheelDrive;
  }

  public void setWheelDrive(EWheelDrive wheelDrive) {
    this.wheelDrive = wheelDrive;
  }

  public EGearboxType getGearboxType() {
    return gearboxType;
  }

  public void setGearboxType(EGearboxType gearboxType) {
    this.gearboxType = gearboxType;
  }

  public Integer getNumberOfGears() {
    return numberOfGears;
  }

  public void setNumberOfGears(Integer numberOfGears) {
    this.numberOfGears = numberOfGears;
  }

  public Collection<CarParameter> getCarParameters() {
    return carParameters;
  }

  public void setCarParameters(
      Collection<CarParameter> carParameters) {
    this.carParameters = carParameters;
  }
}
