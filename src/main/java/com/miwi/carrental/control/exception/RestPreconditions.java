package com.miwi.carrental.control.exception;

import java.util.Optional;
import org.springframework.data.domain.Page;

public final class RestPreconditions {

  private RestPreconditions() {
    throw new AssertionError();
  }

  public static void checkFound(final boolean expression) throws MyResourceNotFoundException {
    if (!expression) {
      throw new MyResourceNotFoundException("Resource not found");
    }
  }

  public static <T> T checkFound(final Optional<T> resource) throws MyResourceNotFoundException {
    if (resource.isEmpty()) {
      throw new MyResourceNotFoundException("Resource not found");
    }
    return resource.get();
  }

  public static <T> Page<T> checkFound(final Page<T> resources) throws MyResourceNotFoundException {
    if (resources.isEmpty()) {
      throw new MyResourceNotFoundException("Resources not found");
    }
    return resources;
  }
}