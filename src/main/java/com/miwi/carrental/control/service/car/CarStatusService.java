package com.miwi.carrental.control.service.car;

import com.miwi.carrental.domain.entity.CarStatus;
import com.miwi.carrental.domain.enums.CarStatusType;
import com.miwi.carrental.control.exception.MyResourceNotFoundException;
import com.miwi.carrental.control.repository.CarStatusDao;
import com.miwi.carrental.control.service.generic.GenericService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CarStatusService  extends GenericService<CarStatus> {

  private final CarStatusDao carStatusDao;

  public CarStatusService(final CarStatusDao carStatusDao) {
    this.carStatusDao = carStatusDao;
  }

  public CarStatus findByCarStatusName(CarStatusType carStatusType) {
    try {
      return checkFound(carStatusDao.findByCarStatusName(carStatusType));
    } catch (MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The car status with type: " + carStatusType + " was not found",
          ex);
    }
  }

  public CarStatus findById(String id) {
    try {
      return checkFound(carStatusDao.findById(id));
    } catch (MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The car status with id: " + id + " was not found",
          ex);
    }
  }

  public List<CarStatus> findAll() {
    return carStatusDao.findAll();
  }
}