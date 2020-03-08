package com.miwi.carrental.service.viewservice;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;

public interface IViewService<T> {

  List<T> findAll();

  Optional<T> findById(Long id);

  List<T> findAllAndSortByParam(String sort, String direction);

}
