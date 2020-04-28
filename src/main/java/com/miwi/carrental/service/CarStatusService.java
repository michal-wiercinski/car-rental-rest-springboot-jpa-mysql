package com.miwi.carrental.service;

import com.miwi.carrental.domain.dto.CarDto;
import com.miwi.carrental.domain.entity.CarStatus;
import com.miwi.carrental.domain.enums.CarStatusType;
import com.miwi.carrental.exception.MyResourceNotFoundException;
import com.miwi.carrental.exception.RestPreconditions;
import com.miwi.carrental.repository.CarStatusDao;
import java.util.List;
import java.util.Optional;
import javax.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CarStatusService {

  private Logger logger = LoggerFactory.getLogger(getClass().getName());
  private final CarStatusDao carStatusDao;

  public CarStatusService(final CarStatusDao carStatusDao) {
    this.carStatusDao = carStatusDao;
  }


  public CarStatus getCarStatusFromCarParamDto(CarDto carDto) {
    return findByCarStatusName(
        CarStatusType.valueOf(carDto.getCarStatus()));
  }

  public CarStatus findByCarStatusName(CarStatusType carStatusType) {
    try {
      return RestPreconditions.checkFound(carStatusDao.findByCarStatusName(carStatusType));
    } catch (MyResourceNotFoundException ex) {
      logger.warn("Car status type: {} is not exists", carStatusType);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The car status with name: " + carStatusType + " was not found",
          ex);
    }
  }


  public CarStatus findById(String id) {
    try {
      return RestPreconditions.checkFound(carStatusDao.findById(id));
    } catch (MyResourceNotFoundException ex) {
      logger.warn("The car status with id: {} was not found ", id);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The car status with id: " + id + " was not found",
          ex);
    }
  }

  public List<CarStatus> findAll() {
    return carStatusDao.findAll();
  }
}