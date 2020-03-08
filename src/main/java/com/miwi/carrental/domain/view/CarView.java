package com.miwi.carrental.domain.view;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.springframework.context.annotation.Scope;

@Scope("request")
@MappedSuperclass
public class CarView {

  @Id
  @Column(name = "car_id")
  private Long id;

  @Column(name = "brand")
  private String brandName;

  @Column(name = "car_model")
  private String carModelName;

  @Column(name = "body_type")
  private String bodyType;

  @Column(name = "engine_power")
  private Integer enginePower;

  @Column(name = "engine_size")
  private Integer engineSize;

  @Column(name = "production_year")
  private Integer prodYear;

  @Column(name = "daily_rate")
  private Integer dailyRate;

  @Column(name = "status")
  private String carStatus;

  @Column(name = "location_name")
  private String locationName;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public String getCarModelName() {
    return carModelName;
  }

  public void setCarModelName(String carModelName) {
    this.carModelName = carModelName;
  }

  public String getBodyType() {
    return bodyType;
  }

  public void setBodyType(String bodyType) {
    this.bodyType = bodyType;
  }

  public Integer getEnginePower() {
    return enginePower;
  }

  public void setEnginePower(Integer enginePower) {
    this.enginePower = enginePower;
  }

  public Integer getEngineSize() {
    return engineSize;
  }

  public void setEngineSize(Integer engineSize) {
    this.engineSize = engineSize;
  }

  public Integer getProdYear() {
    return prodYear;
  }

  public void setProdYear(Integer prodYear) {
    this.prodYear = prodYear;
  }

  public Integer getDailyRate() {
    return dailyRate;
  }

  public void setDailyRate(Integer dailyRate) {
    this.dailyRate = dailyRate;
  }

  public String getCarStatus() {
    return carStatus;
  }

  public void setCarStatus(String carStatus) {
    this.carStatus = carStatus;
  }

  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }
}