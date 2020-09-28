package com.miwi.carrental.models.enums;

public enum ECarStatus {
  AVAILABLE("Available"), UNAVAILABLE("Unavailable");

  private String type;

  private ECarStatus(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
