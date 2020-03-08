package com.miwi.carrental.service.entityservice;

import com.miwi.carrental.domain.entity.CarStatus;
import com.miwi.carrental.repository.dao.CarStatusDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarStatusService {

  private final CarStatusDao carStatusDao;

  public CarStatusService(final CarStatusDao carStatusDao) {
    this.carStatusDao = carStatusDao;
  }

  public Optional<CarStatus> findById(String id) {
    return carStatusDao.findById(id);
  }

  public List<CarStatus> findAll() {
    return carStatusDao.findAll();
  }
}