package com.miwi.carrental.models.entity;

import com.miwi.carrental.models.enums.ECarStatus;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "car_status")
public class CarStatus {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PK_status_code")
  @Id
  private String statusCode;

  @Enumerated(EnumType.STRING)
  @Column(name = "status_description")
  private ECarStatus carStatusName;

  @OneToMany(mappedBy = "carStatus")
  private List<Car> cars;

  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public ECarStatus getCarStatusName() {
    return carStatusName;
  }

  public void setCarStatusName(ECarStatus carStatusName) {
    this.carStatusName = carStatusName;
  }
}