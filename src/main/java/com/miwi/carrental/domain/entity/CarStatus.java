package com.miwi.carrental.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

  @Column(name = "status_description")
  private String statusDescription;

  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public String getStatusDescription() {
    return statusDescription;
  }

  public void setStatusDescription(String statusDescription) {
    this.statusDescription = statusDescription;
  }
}