package com.miwi.carrental.domain.entity;

import com.miwi.carrental.domain.enums.GearboxType;
import com.miwi.carrental.domain.enums.WheelDrive;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

}
