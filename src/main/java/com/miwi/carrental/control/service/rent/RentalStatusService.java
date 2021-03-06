package com.miwi.carrental.control.service.rent;

import com.miwi.carrental.models.entity.RentalStatus;
import com.miwi.carrental.control.exception.MyResourceNotFoundException;
import com.miwi.carrental.control.repository.RentalStatusDao;
import com.miwi.carrental.control.service.generic.GenericService;
import com.miwi.carrental.models.enums.ERentalStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

@Service
public class RentalStatusService extends GenericService<RentalStatus> {

  private Logger logger = LoggerFactory.getLogger(getClass().getName());

  private final RentalStatusDao rentalStatusDao;

  public RentalStatusService(final RentalStatusDao rentalStatusDao) {
    this.rentalStatusDao = rentalStatusDao;
  }

  public RentalStatus findByStatus(ERentalStatus rentalStatus) {
    try {
      return checkFound(rentalStatusDao.findByRentalStatusType(rentalStatus));
    } catch (MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The rental status with type: " + rentalStatus + " was not found",
          ex);
    }
  }
  @Override
  public RentalStatus findById(Long id) {
    try {
      return checkFound(rentalStatusDao.findById(id));
    } catch (MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The rental status with id: " + id + " was not found",
          ex);
    }
  }
}
