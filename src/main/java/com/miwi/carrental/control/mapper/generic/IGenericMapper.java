package com.miwi.carrental.control.mapper.generic;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;

public interface IGenericMapper<T, S> {

  @Transactional
  T mapDtoToEntity(S dto);

  @Transactional
  S mapEntityToDto(T entity);

  @Transactional
  List<S> mapEntityListToListDto(List<T> entityList);

  @Transactional
  Page<S> mapEntityPageToPageDto(Page<T> entityPage);
}
