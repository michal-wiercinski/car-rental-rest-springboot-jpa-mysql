package com.miwi.carrental.models.entity;

import com.miwi.carrental.models.enums.CarStatusType;
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
import lombok.Data;

@Data
@Entity
@Table(name = "car_status")
public class CarStatus {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PK_status_code")
  @Id
  private String statusCode;

  @Enumerated(EnumType.STRING)
  @Column(name = "status_description")
  private CarStatusType carStatusName;

  @OneToMany(mappedBy = "carStatus")
  private List<Car> cars;

  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public CarStatusType getCarStatusName() {
    return carStatusName;
  }

  public void setCarStatusName(CarStatusType carStatusName) {
    this.carStatusName = carStatusName;
  }
}