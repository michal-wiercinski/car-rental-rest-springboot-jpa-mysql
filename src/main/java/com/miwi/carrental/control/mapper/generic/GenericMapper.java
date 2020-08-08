package com.miwi.carrental.control.mapper.generic;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

public class GenericMapper<T, S> implements IGenericMapper<T, S> {

  @Override
  public T mapDtoToEntity(S dto) {
    return null;
  }

  @Override
  public S mapEntityToDto(T entity) {
    return null;
  }

  @Override
  public List<S> mapEntityListToListDto(List<T> entityList) {
    return entityList.stream().map(this::mapEntityToDto).collect(Collectors.toList());
  }

  @Override
  public Page<S> mapEntityPageToPageDto(Page<T> entityPage) {
    return entityPage.map(this::mapEntityToDto);
  }
}
