package com.miwi.carrental.control.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.miwi.carrental.models.enums.ERentalStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "rental", collectionRelation = "rentals")
public class RentalDto extends RepresentationModel<RentalDto> {

  private Long id;

  @JsonProperty(value = "car")
  private CarDto carDto;

  @JsonProperty(value = "user")
  private UserDto userDto;

  private String rentalStatus;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private BigDecimal rentalCost;
  private Integer distance;
  private Boolean isLimitedTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public CarDto getCarDto() {
    return carDto;
  }

  public void setCarDto(CarDto carDto) {
    this.carDto = carDto;
  }

  public UserDto getUserDto() {
    return userDto;
  }

  public void setUserDto(UserDto userDto) {
    this.userDto = userDto;
  }

  public String getRentalStatus() {
    return rentalStatus;
  }

  public void setRentalStatus(String rentalStatus) {
    this.rentalStatus = rentalStatus;
  }

  public LocalDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  public BigDecimal getRentalCost() {
    return rentalCost;
  }

  public void setRentalCost(BigDecimal rentalCost) {
    this.rentalCost = rentalCost;
  }

  public Integer getDistance() {
    return distance;
  }

  public void setDistance(Integer distance) {
    this.distance = distance;
  }

  public Boolean getLimitedTime() {
    return isLimitedTime;
  }

  public void setLimitedTime(Boolean limitedTime) {
    isLimitedTime = limitedTime;
  }
}
