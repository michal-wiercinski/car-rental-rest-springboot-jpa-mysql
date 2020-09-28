package com.miwi.carrental.models.enums;

public enum EFuelType {
  DIESEL("Diesel"), HYBRID("Hybrid"), PETROL("Petrol"), CNG("CNG");

  private final String type;

  private EFuelType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
