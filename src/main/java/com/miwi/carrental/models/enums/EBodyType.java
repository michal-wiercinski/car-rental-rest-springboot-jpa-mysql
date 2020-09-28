package com.miwi.carrental.models.enums;

public enum EBodyType {
  HATCHBACK("Hatchback"), SEDAN("Sedan"), SUV("SUV"), COUPE("Coupe"),
  WAGON("Wagon"), VAN("VAN"), JEEP("Jeep");

  private String type;

  EBodyType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
