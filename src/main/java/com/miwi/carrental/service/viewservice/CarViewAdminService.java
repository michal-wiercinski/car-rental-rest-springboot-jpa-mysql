package com.miwi.carrental.service.viewservice;

import com.miwi.carrental.domain.view.CarViewAdmin;
import com.miwi.carrental.repository.view.CarViewAdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarViewAdminService implements IViewService<CarViewAdmin> {

  @Autowired
  CarViewAdminDao carViewAdminDao;

  @Override
  public List<CarViewAdmin> findAll() {
    return carViewAdminDao.findAll();
  }

  @Override
  public Optional<CarViewAdmin> findById(Long id) {
    return carViewAdminDao.findById(id);
  }

  @Override
  public List<CarViewAdmin> findAllAndSortByParam(String sortingAttribute, String direction) {

    if (!sortingAttribute.isEmpty()) {
      if (!direction.isEmpty() && direction.equals(Direction.DESC.toString())) {
        return carViewAdminDao.findAll(Sort.by(sortingAttribute).descending());
      }
      return carViewAdminDao.findAll(Sort.by(sortingAttribute).ascending());
    }
    return carViewAdminDao.findAll();
  }
}