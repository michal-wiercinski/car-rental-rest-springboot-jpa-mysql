package com.miwi.carrental.domain.dto;

import com.miwi.carrental.validation.OnUpdate;
import javax.validation.constraints.NotNull;

public class CarModelDto {

  private Long id;

  @NotNull(message = "You must enter the name of car model", groups = OnUpdate.class)
  private String carModelName;

  @NotNull(message = "You must enter the name of brand name", groups = OnUpdate.class)
  private String brandName;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCarModelName() {
    return carModelName;
  }

  public void setCarModelName(String carModelName) {
    this.carModelName = carModelName;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }
}
