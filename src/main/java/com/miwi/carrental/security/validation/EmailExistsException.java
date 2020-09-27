package com.miwi.carrental.security.validation;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK)
public class EmailExistsException extends Throwable {

  public EmailExistsException(final String message) {
    super(message);
  }

}