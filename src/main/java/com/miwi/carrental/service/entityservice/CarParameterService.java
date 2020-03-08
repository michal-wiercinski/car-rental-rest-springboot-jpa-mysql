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
    carParameter.setAverageFuelConsumption(carDto.getAverageFuelConsumption());
    carParameter.setCurrentMileage(carDto.getCurrentMileage());
    carParameter.setEngineSize(carDto.getEngineSize());
    carParameter.setDailyRate(carDto.getDailyRate());
    carParameter.setPower(carDto.getPower());
    return carParameter;
  }
}