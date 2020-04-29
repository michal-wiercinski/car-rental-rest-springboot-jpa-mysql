package com.miwi.carrental.service;

import com.miwi.carrental.domain.entity.CarModel;
import com.miwi.carrental.exception.MyResourceNotFoundException;
import com.miwi.carrental.exception.RestPreconditions;
import com.miwi.carrental.repository.CarModelDao;
import com.miwi.carrental.service.generic.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CarModelService extends GenericService<CarModel> {

  private Logger logger = LoggerFactory.getLogger(getClass().getName());

  private final CarModelDao carModelDao;

  public CarModelService(final CarModelDao carModelDao) {
    this.carModelDao = carModelDao;
  }

  public CarModel findByName(String carModelName) {
    Optional<CarModel> carModel = carModelDao.findByName(carModelName);

    return carModel.orElse(new CarModel());
  }

  @Override
  public List<CarModel> findAll() {
    return carModelDao.findAll();
  }

  @Override
  public CarModel findById(Long id) {
    try {
      return checkFound(carModelDao.findById(id));
    } catch (MyResourceNotFoundException ex) {
      logger.warn("The car model with id: {} was not found ", id);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The car model with id: " + id + " was not found",
          ex);
    }
  }
}
