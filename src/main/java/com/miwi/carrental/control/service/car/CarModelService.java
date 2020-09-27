package com.miwi.carrental.control.service.car;

import com.miwi.carrental.models.entity.CarModel;
import com.miwi.carrental.control.exception.MyResourceNotFoundException;
import com.miwi.carrental.control.repository.CarModelDao;
import com.miwi.carrental.control.repository.CarModelDaoImpl;
import com.miwi.carrental.control.service.generic.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CarModelService extends GenericService<CarModel> {

  private static final int RESULTS_LIMIT_FOR_AUTOCOMPLETION = 10;
  private Logger logger = LoggerFactory.getLogger(getClass().getName());

  private final CarModelDao carModelDao;
  private final CarModelDaoImpl carModelDaoImpl;

  public CarModelService(final CarModelDao carModelDao,
      CarModelDaoImpl carModelDaoImpl) {
    this.carModelDao = carModelDao;
    this.carModelDaoImpl = carModelDaoImpl;
  }

  public List<CarModel> findByParamContaining(String param) {
    try {
      return checkFound(
          carModelDaoImpl.searchByCarModelNameOrBrandName(param, RESULTS_LIMIT_FOR_AUTOCOMPLETION));
    } catch (MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The cars model with name: " + param + " were not found",
          ex);
    }
  }

  public CarModel findByName(String carModelName) {
    try {
      return checkFound(carModelDao.findByName(carModelName));
    } catch (MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The car model with name: " + carModelName + " was not found",
          ex);
    }
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
