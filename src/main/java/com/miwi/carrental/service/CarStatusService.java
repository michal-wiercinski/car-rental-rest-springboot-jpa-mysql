package com.miwi.carrental.service;

import com.miwi.carrental.domain.dto.CarDto;
import com.miwi.carrental.domain.entity.CarStatus;
import com.miwi.carrental.domain.enums.CarStatusType;
import com.miwi.carrental.repository.CarStatusDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarStatusService {

  private final CarStatusDao carStatusDao;

  public CarStatusService(final CarStatusDao carStatusDao) {
    this.carStatusDao = carStatusDao;
  }


  public CarStatus getCarStatusFromCarParamDto(CarDto carDto) {
    Optional<CarStatus> carStatus = findByCarStatusName(
        CarStatusType.valueOf(carDto.getCarStatus()));

    return carStatus.orElseGet(() -> findByCarStatusName(CarStatusType.UNAVAILABLE).get());
  }

  public Optional<CarStatus> getCarStatusFromParam(String availabilityParam) {
    return carStatusDao.findByCarStatusName(CarStatusType.valueOf(availabilityParam));
  }


  public Optional<CarStatus> findByCarStatusName(CarStatusType carStatusType) {
    return carStatusDao.findByCarStatusName(carStatusType);
  }

  public Optional<CarStatus> findById(String id) {
    return carStatusDao.findById(id);
  }

  public List<CarStatus> findAll() {
    return carStatusDao.findAll();
  }
}