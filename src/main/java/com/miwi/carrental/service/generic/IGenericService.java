package com.miwi.carrental.service.generic;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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