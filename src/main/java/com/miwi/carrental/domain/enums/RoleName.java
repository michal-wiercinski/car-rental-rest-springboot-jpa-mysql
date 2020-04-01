package com.miwi.carrental.domain.enums;

public enum RoleName {
  SUPER_ADMIN("ROLE_SUPER_ADMIN"), ADMIN("ROLE_ADMIN"), USER("ROLE_USER");
  private String roleName;

  private RoleName(String role) {
    this.roleName = role;
  }

  public String getRoleName() {
    return roleName;
  }
}
