package com.miwi.carrental.models.enums;

public enum FuelType {
  DIESEL("Diesel"), HYBRID("Hybrid"), PETROL("Petrol"), CNG("CNG");

  private final String type;

  private FuelType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
