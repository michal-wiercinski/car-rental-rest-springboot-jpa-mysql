package com.miwi.carrental.control.dto.password;

public class PasswordChangeDto extends PasswordDto {

  private String oldPassword;

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }
}
