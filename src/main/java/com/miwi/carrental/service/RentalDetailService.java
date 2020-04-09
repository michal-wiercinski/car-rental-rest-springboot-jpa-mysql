package com.miwi.carrental.service;

import com.miwi.carrental.domain.entity.RentalDetails;
import com.miwi.carrental.repository.RentalDetailsDao;
import com.miwi.carrental.service.generic.GenericService;
import org.springframework.stereotype.Service;

@Service
public class RentalDetailService extends GenericService<RentalDetails> {

  private final RentalDetailsDao rentalDetailsDao;

  public RentalDetailService(
      final RentalDetailsDao rentalDetailsDao) {
    this.rentalDetailsDao = rentalDetailsDao;
  }

  public void updateDate(Long id) {
    rentalDetailsDao.updateEndDateById(id);
  }

  @Override
  public RentalDetails save(RentalDetails entity) {
    return rentalDetailsDao.save(entity);
  }

}
