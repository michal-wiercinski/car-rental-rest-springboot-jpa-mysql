package com.miwi.carrental.domain.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CarDto {

  private Long id;

  @NotNull(message = "The registration number can't be null")
  @Size(min = 4, max = 7, message = "The registration number must have a minimum of 3 and a maximum of 7 letters")
  private String registrationNumber;

  private LocationDto locationDto;

  private CarModelDto carModelDto;

  private CarParameterDto carParameterDto;

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

  public CarModelDto getCarModelDto() {
    return carModelDto;
  }

  public void setCarModelDto(CarModelDto carModelDto) {
    this.carModelDto = carModelDto;
  }

  public CarParameterDto getCarParameterDto() {
    return carParameterDto;
  }

  public void setCarParameterDto(CarParameterDto carParameterDto) {
    this.carParameterDto = carParameterDto;
  }

  public LocationDto getLocationDto() {
    return locationDto;
  }

  public void setLocationDto(LocationDto locationDto) {
    this.locationDto = locationDto;
  }
}
