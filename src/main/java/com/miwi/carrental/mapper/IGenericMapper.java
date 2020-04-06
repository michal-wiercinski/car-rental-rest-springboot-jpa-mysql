package com.miwi.carrental.mapper;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

public interface IGenericMapper<T, S> {

  @Transactional
  T mapDtoToEntity(S dto);

  @Transactional
  S mapEntityToDto(T entity);

  @Transactional
  List<S> mapEntityListToListDto(List<T> entityList);

}
