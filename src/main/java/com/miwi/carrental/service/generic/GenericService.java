package com.miwi.carrental.service.generic;

import java.util.List;
import java.util.Optional;

public class GenericService<T> implements IGenericService<T> {

  @Override
  public List<T> findAll() {
    return null;
  }

  @Override
  public T save(T entity) {
    return null;
  }

  @Override
  public Optional<T> findById(Long id) {
    return Optional.empty();
  }

  @Override
  public void delete(T entity) {

  }

  @Override
  public void deleteById(Long id) {

  }
}
