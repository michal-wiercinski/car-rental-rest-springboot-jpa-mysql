package com.miwi.carrental.service.entityservice;

import com.miwi.carrental.domain.entity.RentalStatus;
import com.miwi.carrental.domain.enums.RentalStatusType;
import com.miwi.carrental.repository.dao.RentalStatusDao;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RentalStatusService {

  private final RentalStatusDao rentalStatusDao;

  public RentalStatusService(final RentalStatusDao rentalStatusDao) {
    this.rentalStatusDao = rentalStatusDao;
  }

  public Optional<RentalStatus> findByRentalStatusType(RentalStatusType rentalStatusType) {
    return rentalStatusDao.findByRentalStatusType(rentalStatusType);
  }

  public Optional<RentalStatus> findById(Long id) {
    return rentalStatusDao.findById(id);
  }
}
