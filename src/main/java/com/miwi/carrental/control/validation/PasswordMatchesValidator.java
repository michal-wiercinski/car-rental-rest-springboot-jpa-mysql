package com.miwi.carrental.control.validation;


import com.miwi.carrental.control.dto.password.PasswordDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

  @Override
  public void initialize(final PasswordMatches constraintAnnotation) {
  }

  @Override
  public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
    final PasswordDto passwordDto = (PasswordDto) obj;
    return passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword());
  }
}