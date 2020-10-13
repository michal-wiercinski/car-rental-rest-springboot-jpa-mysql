package com.miwi.carrental.control.dto.password;

public class PasswordResetDto extends PasswordDto {

  private String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}