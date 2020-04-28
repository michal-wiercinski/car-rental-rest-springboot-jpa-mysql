package com.miwi.carrental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found" )
public final class MyResourceNotFoundException extends Exception {


  public MyResourceNotFoundException(final String message) {
    super(message);
  }

}