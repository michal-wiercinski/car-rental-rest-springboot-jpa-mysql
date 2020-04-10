package com.miwi.carrental.domain.dto;

import com.miwi.carrental.domain.enums.CarStatusType;
import com.miwi.carrental.validation.OnUpdate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CarDto {

  private Long id;

  @NotNull(message = "The registration number can't be null", groups = OnUpdate.class)
  @Size(min = 4, max = 7, message = "The registration number must have a minimum of 3 and a maximum of 7 letters")
  private String registrationNumber;

  @NotNull(message = "The car status can't be null", groups = OnUpdate.class)
  private String carStatus;

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

  public String getCarStatus() {
    return carStatus;
  }

  public void setCarStatus(String carStatus) {
    this.carStatus = carStatus;
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
