package com.miwi.carrental.domain.enums;

public enum RoleName {
  SUPER_ADMIN("SUPER_ADMIN"), ADMIN("ADMIN"), USER("USER");
  private String roleName;

  private RoleName(String role) {
    this.roleName = role;
  }

  public String getRoleName() {
    return roleName;
  }
}
