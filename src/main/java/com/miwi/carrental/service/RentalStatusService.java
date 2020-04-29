package com.miwi.carrental.service;

import com.miwi.carrental.domain.entity.RentalStatus;
import com.miwi.carrental.domain.enums.RentalStatusType;
import com.miwi.carrental.exception.MyResourceNotFoundException;
import com.miwi.carrental.repository.RentalStatusDao;
import com.miwi.carrental.service.generic.GenericService;
import javax.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RentalStatusService extends GenericService<RentalStatus> {

  private Logger logger = LoggerFactory.getLogger(getClass().getName());

  private final RentalStatusDao rentalStatusDao;

  public RentalStatusService(final RentalStatusDao rentalStatusDao) {
    this.rentalStatusDao = rentalStatusDao;
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
