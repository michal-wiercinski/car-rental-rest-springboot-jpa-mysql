package com.miwi.carrental.domain.entity;

import com.miwi.carrental.domain.enums.GearboxType;
import com.miwi.carrental.domain.enums.WheelDrive;
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
  private WheelDrive wheelDrive;

  @Enumerated(EnumType.STRING)
  @Column(name = "gearbox_type")
  private GearboxType gearboxType;

  @OneToMany(mappedBy = "driveTrain")
  private Collection<CarParameter> carParameters;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public WheelDrive getWheelDrive() {
    return wheelDrive;
  }

  public void setWheelDrive(WheelDrive wheelDrive) {
    this.wheelDrive = wheelDrive;
  }

  public GearboxType getGearboxType() {
    return gearboxType;
  }

  public void setGearboxType(GearboxType gearboxType) {
    this.gearboxType = gearboxType;
  }

  public Collection<CarParameter> getCarParameters() {
    return carParameters;
  }

  public void setCarParameters(
      Collection<CarParameter> carParameters) {
    this.carParameters = carParameters;
  }
}
