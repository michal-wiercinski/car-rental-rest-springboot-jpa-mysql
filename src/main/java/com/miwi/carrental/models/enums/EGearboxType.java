package com.miwi.carrental.models.enums;

public enum EGearboxType {
  MANUAL("Manual"), AUTOMATIC("Automatic");

  private final String type;

  private EGearboxType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
