package com.miwi.carrental.domain.enums;

public enum GearboxType {
  MANUAL("Manual"), AUTOMATIC("Automatic");

  private final String type;

  private GearboxType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
