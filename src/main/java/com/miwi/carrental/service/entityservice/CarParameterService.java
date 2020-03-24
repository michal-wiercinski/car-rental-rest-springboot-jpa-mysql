package com.miwi.carrental.service.entityservice;

import com.miwi.carrental.domain.dto.CarDto;
import com.miwi.carrental.domain.entity.CarParameter;
import com.miwi.carrental.repository.dao.CarParameterDao;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CarParameterService {

  private final CarParameterDao carParameterDao;
  private final BodyTypeService bodyTypeService;
  private final CarStatusService carStatusService;

  public CarParameterService(final CarParameterDao carParameterDao,
      final BodyTypeService bodyTypeService,
      final CarStatusService carStatusService) {
    this.carParameterDao = carParameterDao;
    this.bodyTypeService = bodyTypeService;
    this.carStatusService = carStatusService;
  }

  @Transactional
  public CarParameter save(CarParameter carParameter) {
    return carParameterDao.save(carParameter);
  }

  public CarParameter createNewParameter(CarDto carDto) {
    CarParameter carParameter = new CarParameter();

    carParameter.setBodyType(bodyTypeService.findById(carDto.getBodyTypeDtoId()).get());
    carParameter.setCarStatus(carStatusService.findById(carDto.getCarStatus()).get());
    carParameter.setYearOfProd(carDto.getYearOfProd());
    carParameter.setDailyRate(carDto.getDailyRate());
    return carParameter;
  }

  public CarParameter editCarParameter(CarDto carDto) {
    CarParameter carParameter = new CarParameter();

    if (carDto.getDailyRate() != null) {
      carParameter.setDailyRate(carDto.getDailyRate());
    }
    if (carDto.getCarStatus() != null) {
      carParameter.setCarStatus(carStatusService.findById(carDto.getCarStatus()).get());
    }
    if (carDto.getBodyTypeDtoId() != null) {
      carParameter.setBodyType(bodyTypeService.findById(carDto.getBodyTypeDtoId()).get());
    }
    if (carDto.getYearOfProd() != null) {
      carParameter.setYearOfProd(carDto.getYearOfProd());
    }
  /*  if (carDto.getAverageFuelConsumption() != null) {
      carParameter.setAverageFuelConsumption(carDto.getAverageFuelConsumption());
    }
    if (carDto.getCurrentMileage() != null) {
      carParameter.setCurrentMileage(carDto.getCurrentMileage());
    }
    if (carDto.getEngineSize() != null) {
      carParameter.setEngineSize(carDto.getEngineSize());
    }
    if (carDto.getPower() != null) {
      carParameter.setPower(carDto.getPower());
    }
  */
    return carParameter;
  }
}