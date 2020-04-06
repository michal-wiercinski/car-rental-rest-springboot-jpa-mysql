package com.miwi.carrental.service.entityservice;

import com.miwi.carrental.domain.dto.CarDto;
import com.miwi.carrental.domain.dto.CarParameterDto;
import com.miwi.carrental.domain.entity.CarParameter;
import com.miwi.carrental.mapper.CarParameterDtoMapper;
import com.miwi.carrental.repository.dao.CarParameterDao;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CarParameterService {

  private final CarParameterDao carParameterDao;
  private final BodyTypeService bodyTypeService;
  private final CarParameterDtoMapper carParameterDtoMapper;
  private final EngineService engineService;
  private final DriveTrainService driveTrainService;

  public CarParameterService(final CarParameterDao carParameterDao,
      final BodyTypeService bodyTypeService,
      final CarParameterDtoMapper carParameterDtoMapper,
      EngineService engineService,
      DriveTrainService driveTrainService) {
    this.carParameterDao = carParameterDao;
    this.bodyTypeService = bodyTypeService;
    this.carParameterDtoMapper = carParameterDtoMapper;
    this.engineService = engineService;
    this.driveTrainService = driveTrainService;
  }

  @Transactional
  public CarParameter save(CarParameter carParameter) {
    return carParameterDao.save(carParameter);
  }

  public CarParameter createNewParameter(CarDto carDto) {
    return carParameterDtoMapper.mapDtoToEntity(carDto.getCarParameterDto());
  }

  public CarParameter editCarParameterByCarDto(CarParameterDto carParameterDto) {
    CarParameter carParameter = new CarParameter();

    if (carParameterDto.getDailyRate() != null) {
      carParameter.setDailyRate(carParameterDto.getDailyRate());
    }
    if (carParameterDto.getColor() != null) {
      carParameter.setColor(carParameterDto.getColor());
    }
    if (carParameterDto.getYearOfProd() != null) {
      carParameter.setYearOfProd(carParameterDto.getYearOfProd());
    }
    if (carParameterDto.getCurrentMileage() != null) {
      carParameter.setCurrentMileage(carParameterDto.getCurrentMileage());
    }

    bodyTypeService.editBodyTypeByDto(carParameterDto);
    engineService.editEngineByDto(carParameterDto);
    driveTrainService.editDriveTrainByDto(carParameterDto);

    return carParameter;
  }
}