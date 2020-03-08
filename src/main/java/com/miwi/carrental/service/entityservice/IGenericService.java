package com.miwi.carrental.service.entityservice;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

public interface IGenericService<T> {

  List<T> findAll();

  @Transactional
  T save(T entity);

  @Transactional
  Optional<T> findById(Long id);

  @Transactional
  void delete(T entity);

  @Transactional
  void deleteById(Long id);

}