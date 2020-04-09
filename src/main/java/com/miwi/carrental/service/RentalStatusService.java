package com.miwi.carrental.service;

import com.miwi.carrental.domain.entity.RentalStatus;
import com.miwi.carrental.domain.enums.RentalStatusType;
import com.miwi.carrental.repository.RentalStatusDao;
import com.miwi.carrental.service.generic.GenericService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RentalStatusService extends GenericService<RentalStatus> {

  private final RentalStatusDao rentalStatusDao;

  public RentalStatusService(final RentalStatusDao rentalStatusDao) {
    this.rentalStatusDao = rentalStatusDao;
  }

  public Optional<RentalStatus> findByRentalStatusType(RentalStatusType rentalStatusType) {
    return rentalStatusDao.findByRentalStatusType(rentalStatusType);
  }

  @Override
  public Optional<RentalStatus> findById(Long id) {
    return rentalStatusDao.findById(id);
  }
}
