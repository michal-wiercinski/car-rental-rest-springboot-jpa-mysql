package com.miwi.carrental.service;

import com.miwi.carrental.domain.entity.CarModel;
import com.miwi.carrental.repository.CarModelDao;
import com.miwi.carrental.service.generic.GenericService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarModelService extends GenericService<CarModel> {

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
  public Optional<CarModel> findById(Long id) {
    return carModelDao.findById(id);
  }

}