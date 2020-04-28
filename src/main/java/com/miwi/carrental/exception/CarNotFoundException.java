package com.miwi.carrental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "")
public class CarNotFoundException extends RuntimeException {

  public CarNotFoundException(Long id) {
    super("Car id not found: " + id);
  }
}
