package com.miwi.carrental.domain.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class CarDto {

  private Long id;

  @NotNull(message = "The registration number can't be null")
  @Size(min = 4, max = 7, message = "The registration number must have a minimum of 3 and a maximum of 7 letters")
  private String registrationNumber;

  @NotNull(message = "The daily rate can't be null")
  private Integer dailyRate;

  @NotNull(message = "The body type can't be null")
  private Long bodyTypeDtoId;

  @NotNull(message = "The location can't be null")
  private Long LocationDtoId;

  @NotNull(message = "The car model can't be null")
  private Long carModelDtoId;

  @NotNull(message = "The current mileage can't be null")
  private Integer currentMileage;

  @NotNull(message = "The engine size can't be null")
  @Range(min = 1000, max = 10000, message = "The engine size must have a minimum of 1000 and a maximum of 10000 cm3")
  private Integer engineSize;

  @NotNull
  @Range(min = 50, max = 400, message = "The engine power must have a minimum of 50 and a maximum of 400 HP")
  private Integer power;

  @NotNull(message = "The year of production can't be null")
  @Range(min = 1950, max = 2100, message = "The year of production must be a minimum of 1950 and a maximum of 2100")
  private Integer yearOfProd;

  @NotNull(message = "The average fuel consumption can't be null")
  private Double averageFuelConsumption;

  @NotNull(message = "The car status can't be null")
  private String carStatus;


  public CarDto() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRegistrationNumber() {
    return registrationNumber;
  }

  public void setRegistrationNumber(String registrationNumber) {
    this.registrationNumber = registrationNumber;
  }

  public Integer getDailyRate() {
    return dailyRate;
  }

  public void setDailyRate(Integer dailyRate) {
    this.dailyRate = dailyRate;
  }

  public Long getBodyTypeDtoId() {
    return bodyTypeDtoId;
  }

  public void setBodyTypeDtoId(Long bodyTypeDtoId) {
    this.bodyTypeDtoId = bodyTypeDtoId;
  }

  public Long getLocationDtoId() {
    return LocationDtoId;
  }

  public void setLocationDtoId(Long locationDtoId) {
    LocationDtoId = locationDtoId;
  }

  public Long getCarModelDtoId() {
    return carModelDtoId;
  }

  public void setCarModelDtoId(Long carModelDtoId) {
    this.carModelDtoId = carModelDtoId;
  }

  public Integer getCurrentMileage() {
    return currentMileage;
  }

  public void setCurrentMileage(Integer currentMileage) {
    this.currentMileage = currentMileage;
  }

  public Integer getEngineSize() {
    return engineSize;
  }

  public void setEngineSize(Integer engineSize) {
    this.engineSize = engineSize;
  }

  public Integer getYearOfProd() {
    return yearOfProd;
  }

  public void setYearOfProd(Integer yearOfProd) {
    this.yearOfProd = yearOfProd;
  }

  public Double getAverageFuelConsumption() {
    return averageFuelConsumption;
  }

  public void setAverageFuelConsumption(Double averageFuelConsumption) {
    this.averageFuelConsumption = averageFuelConsumption;
  }

  public Integer getPower() {
    return power;
  }

  public void setPower(Integer power) {
    this.power = power;
  }

  public String getCarStatus() {
    return carStatus;
  }

  public void setCarStatus(String carStatus) {
    this.carStatus = carStatus;
  }
}
