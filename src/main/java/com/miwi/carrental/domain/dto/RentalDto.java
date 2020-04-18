package com.miwi.carrental.domain.dto;

import com.miwi.carrental.domain.enums.RentalStatusType;
import java.math.BigDecimal;
import java.sql.Timestamp;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "rental", collectionRelation = "rentals")
public class RentalDto extends RepresentationModel<RentalDto> {

  private CarDto carDto;
  private UserDto userDto;
  private RentalStatusType rentalStatusType;
  private Timestamp startDate;
  private Timestamp endDate;
  private BigDecimal rentalCost;
  private Integer distance;


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

  public RentalStatusType getRentalStatusType() {
    return rentalStatusType;
  }

  public void setRentalStatusType(RentalStatusType rentalStatusType) {
    this.rentalStatusType = rentalStatusType;
  }

  public Timestamp getStartDate() {
    return startDate;
  }

  public void setStartDate(Timestamp startDate) {
    this.startDate = startDate;
  }

  public Timestamp getEndDate() {
    return endDate;
  }

  public void setEndDate(Timestamp endDate) {
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
}
