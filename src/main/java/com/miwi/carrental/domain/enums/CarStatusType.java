package com.miwi.carrental.domain.enums;

public enum CarStatusType {
  AVAILABLE("Available"), UNAVAILABLE("Unavailable");

  private String type;

  private CarStatusType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
