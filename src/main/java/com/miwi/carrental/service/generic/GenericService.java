package com.miwi.carrental.service.generic;

import com.miwi.carrental.exception.MyResourceNotFoundException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

public class GenericService<T> implements IGenericService<T> {

  private Logger logger = LoggerFactory.getLogger(getClass().getName());


  @Override
  public List<T> findAll() {
    return null;
  }

  @Override
  public T save(T entity) {
    return null;
  }

  @Override
  public T findById(Long id) {
    return null;
  }

  @Override
  public void delete(T entity) {

  }

  @Override
  public void deleteById(Long id) {
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
