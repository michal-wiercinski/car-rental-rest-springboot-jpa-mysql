package com.miwi.carrental.web.utils;

import com.miwi.carrental.security.payload.response.MessageResponse;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;

public class CheckerOfRequest {

  private static Logger logger = LoggerFactory.getLogger(CheckerOfRequest.class);

  public static MessageResponse checkErrors(BindingResult bindingResult) {
    Map<String, String> errors = new HashMap<>();
    if (bindingResult.hasErrors()) {
      bindingResult.getFieldErrors().forEach(error -> {
            if (errors.containsKey(error.getField())) {
              errors.put(error.getField(),
                  String.format("%s, %s", errors.get(error.getField()), error.getDefaultMessage()));
            } else {
              errors.put(error.getField(), error.getDefaultMessage());
            }
            logger.warn("Validation failed - Object '{}': {}",
                error.getField().substring(0, 1).toUpperCase() + error.getField().substring(1), error.getDefaultMessage());
          }
      );
    }
    return new MessageResponse(errors, "VALIDATION_FAILED", true);
  }
}