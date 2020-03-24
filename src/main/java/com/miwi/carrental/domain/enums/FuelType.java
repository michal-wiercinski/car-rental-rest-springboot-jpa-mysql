package com.miwi.carrental.domain.enums;

public enum FuelType {
  DIESEL("Diesel"), HYBRID("Hybrid"), PETROL("Petrol"), CNG("CNG");

  private final String type;

  FuelType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
