package com.miwi.carrental.control.dto.password;

import com.miwi.carrental.control.validation.PasswordMatches;
import com.miwi.carrental.control.validation.ValidPassword;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@PasswordMatches(message = "Passwords dont match")
public class PasswordDto {

  @ValidPassword
  @NotNull
  @NotBlank(message = "New password is mandatory")
  private String newPassword;

  @ValidPassword
  @NotNull
  @NotBlank(message = "New password is mandatory")
  private String confirmPassword;

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
}
