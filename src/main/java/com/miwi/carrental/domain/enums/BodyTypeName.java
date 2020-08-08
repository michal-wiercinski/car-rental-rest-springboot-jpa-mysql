package com.miwi.carrental.domain.enums;

public enum BodyTypeName {
  HATCHBACK("Hatchback"), SEDAN("Sedan"), SUV("SUV"), COUPE("Coupe"),
  WAGON("Wagon"), VAN("VAN"), JEEP("Jeep");

  private String type;

  BodyTypeName(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
