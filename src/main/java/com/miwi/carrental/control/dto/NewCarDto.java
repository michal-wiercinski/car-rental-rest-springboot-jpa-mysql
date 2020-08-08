package com.miwi.carrental.control.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.miwi.carrental.control.validation.OnUpdate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class NewCarDto {

  @JsonProperty
  @NotNull
  private Long carModelId;

  @JsonProperty
  @NotNull
  private Long locationId;

  @JsonProperty
  @NotNull(message = "The registration number can't be null", groups = OnUpdate.class)
  @Size(min = 4, max = 7, message = "The registration number must have a minimum of 3 and a maximum of 7 letters")
  private String registrationNumber;

  @NotNull(message = "The car status can't be null", groups = OnUpdate.class)
  private String carStatus;

  @JsonProperty(value = "carParameter")
  private CarParameterDto carParameterDto;

  public Long getCarModelId() {
    return carModelId;
  }

  public void setCarModelId(Long carModelId) {
    this.carModelId = carModelId;
  }

  public Long getLocationId() {
    return locationId;
  }

  public void setLocationId(Long locationId) {
    this.locationId = locationId;
  }

  public String getRegistrationNumber() {
    return registrationNumber;
  }

  public void setRegistrationNumber(String registrationNumber) {
    this.registrationNumber = registrationNumber;
  }

  public String getCarStatus() {
    return carStatus;
  }

  public void setCarStatus(String carStatus) {
    this.carStatus = carStatus;
  }

  public CarParameterDto getCarParameterDto() {
    return carParameterDto;
  }

  public void setCarParameterDto(CarParameterDto carParameterDto) {
    this.carParameterDto = carParameterDto;
  }
}