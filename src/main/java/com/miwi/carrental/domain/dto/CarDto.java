package com.miwi.carrental.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.miwi.carrental.validation.OnUpdate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "car", collectionRelation = "cars ")
public class CarDto extends RepresentationModel<CarDto> {

  private Long id;

  @NotNull(message = "The registration number can't be null", groups = OnUpdate.class)
  @Size(min = 4, max = 7, message = "The registration number must have a minimum of 3 and a maximum of 7 letters")
  private String registrationNumber;

  @NotNull(message = "The car status can't be null", groups = OnUpdate.class)
  private String carStatus;

  @JsonProperty(value = "location")
  private LocationDto locationDto;

  @JsonProperty(value = "carModel")
  private CarModelDto carModelDto;

  @JsonProperty(value = "carParameter")
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
