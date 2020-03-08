package com.miwi.carrental.service.viewservice;

import com.miwi.carrental.domain.view.CarViewUser;
import com.miwi.carrental.repository.view.CarViewUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarViewUserService implements IViewService<CarViewUser> {

  private final String STATUS = "available";

  @Autowired
  private CarViewUserDao carViewUserDao;

  @Override
  public List<CarViewUser> findAll() {
    return carViewUserDao.findAll();
  }

  public List<CarViewUser> findAllAvailable() {
    return carViewUserDao.findCarViewUserByCarStatusIsLike(STATUS);
  }

  public List<CarViewUser> findAllAvailable(String sort, String direction) {

    if (!sort.isEmpty()) {
      if (!direction.isEmpty() && direction.equals(Direction.DESC.toString())) {
        return carViewUserDao.findCarViewUserByCarStatusIsLike(STATUS, Sort.by(sort).descending());
      }
      return carViewUserDao.findCarViewUserByCarStatusIsLike(STATUS, Sort.by(sort).ascending());
    }
    return carViewUserDao.findCarViewUserByCarStatusIsLike(STATUS);
  }

  @Override
  public Optional<CarViewUser> findById(Long id) {
    return Optional.empty();
  }

  @Override
  public List<CarViewUser> findAllAndSortByParam(String sort, String direction) {
    if (!sort.isEmpty()) {
      if (!direction.isEmpty() && direction.equals(Direction.DESC.toString())) {
        return carViewUserDao.findAll(Sort.by(sort).descending());
      }
      return carViewUserDao.findAll(Sort.by(sort).ascending());
    }
    return carViewUserDao.findAll();
  }
}