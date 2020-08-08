package com.miwi.carrental.control.service.car;

import com.miwi.carrental.control.dto.CarDto;
import com.miwi.carrental.control.dto.CarParameterDto;
import com.miwi.carrental.domain.entity.CarParameter;
import com.miwi.carrental.control.mapper.dto.CarParameterDtoMapper;
import com.miwi.carrental.control.repository.CarParameterDao;
import com.miwi.carrental.control.service.generic.GenericService;
import org.springframework.stereotype.Service;

@Service
public class CarParameterService extends GenericService<CarParameter> {

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

    carParameter.setBodyType(bodyTypeService.editBodyTypeByDto(carParameterDto));
    carParameter.setEngine(engineService.editEngineByDto(carParameterDto));
    carParameter.setDriveTrain(driveTrainService.editDriveTrainByDto(carParameterDto));

    return carParameter;
  }

  @Override
  public CarParameter save(CarParameter carParameter) {
    return carParameterDao.save(carParameter);
  }
}