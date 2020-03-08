package com.miwi.carrental.service.viewservice;

import com.miwi.carrental.domain.view.RentalView;
import com.miwi.carrental.repository.view.RentalViewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalViewService implements IViewService<RentalView> {

  @Autowired
  RentalViewDao rentalViewDao;

  @Override
  public List<RentalView> findAll() {
    return rentalViewDao.findAll();
  }

  @Override
  public Optional<RentalView> findById(Long id) {
    return Optional.empty();
  }


  @Override
  public List<RentalView> findAllAndSortByParam(String sort, String direction) {
    if (!sort.isEmpty()) {
      if (!direction.isEmpty() && direction.equals(Direction.DESC.toString())) {
        return rentalViewDao.findAll(Sort.by(sort).descending());
      }
      return rentalViewDao.findAll(Sort.by(sort).ascending());
    }
    return rentalViewDao.findAll();
  }

  public List<RentalView> findAllByEmailAndSortByParam(String email, String sort,
      String direction) {
    if (!sort.isEmpty()) {
      if (!direction.isEmpty() && direction.equals(Direction.DESC.toString())) {
        return rentalViewDao.findByUserEmail(email, Sort.by(sort).descending());
      }
      return rentalViewDao.findByUserEmail(email, Sort.by(sort).ascending());
    }
    return rentalViewDao.findByUserEmail(email);
  }

  public List<RentalView> findByEmail(String email) {
    return rentalViewDao.findByUserEmail(email);
  }
}