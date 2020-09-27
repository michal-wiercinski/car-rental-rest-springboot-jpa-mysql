package com.miwi.carrental.models.enums;

public enum WheelDrive {
  FRONT_WHEEL_DRIVE("Front-wheel drive"), REAR_WHEEL_DRIVE("Rear-wheel drive"),  FOUR_WHEEL_DRIVE("4x4");

  private final String type;

  private WheelDrive(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
