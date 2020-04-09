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
    Optional<CarStatus> carStatus = findByCarStatusName(carDto.getCarStatus());

    return carStatus.orElseGet(() -> findByCarStatusName(CarStatusType.UNAVAILABLE).get());
  }

  public CarStatus getCarStatusFromParam(Optional<String> availabilityParam) {
    Optional<CarStatus> carStatus;
    return availabilityParam.map(s -> findByCarStatusName(CarStatusType.valueOf(s)).get())
        .orElseGet(() -> findByCarStatusName(CarStatusType.AVAILABLE).get());
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