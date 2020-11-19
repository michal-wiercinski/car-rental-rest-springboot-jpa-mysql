package com.miwi.carrental.control.service.car;

import com.miwi.carrental.control.dto.CarParameterDto;
import com.miwi.carrental.control.exception.MyResourceNotFoundException;
import com.miwi.carrental.control.mapper.dto.CarParameterDtoMapper;
import com.miwi.carrental.control.repository.CarParameterDao;
import com.miwi.carrental.control.repository.CarParameterDaoImpl;
import com.miwi.carrental.control.service.generic.GenericService;
import com.miwi.carrental.models.entity.CarParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CarParameterService extends GenericService<CarParameter> {

  private final CarParameterDao carParameterDao;
  private final BodyTypeService bodyTypeService;
  private final CarParameterDtoMapper carParameterDtoMapper;
  private final EngineService engineService;
  private final DriveTrainService driveTrainService;
  private final CarParameterDaoImpl carParameterDaoImpl;

  public CarParameterService(final CarParameterDao carParameterDao,
      final BodyTypeService bodyTypeService,
      final CarParameterDtoMapper carParameterDtoMapper,
      final EngineService engineService,
      final DriveTrainService driveTrainService,
      final CarParameterDaoImpl carParameterDaoImpl) {
    this.carParameterDao = carParameterDao;
    this.bodyTypeService = bodyTypeService;
    this.carParameterDtoMapper = carParameterDtoMapper;
    this.engineService = engineService;
    this.driveTrainService = driveTrainService;
    this.carParameterDaoImpl = carParameterDaoImpl;
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

  public void addDistanceToMileage(Long id, Integer distance){
    CarParameter carParameter = getByRentalDetailsId(id);
    int newMileage = carParameter.getCurrentMileage() + distance;
    carParameter.setCurrentMileage(newMileage);

    save(carParameter);
  }

  public CarParameter getByRentalDetailsId(Long id) {
    try {
      return checkFound(carParameterDaoImpl.findByRentalDetailsId(id));
    } catch (MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The car parameter with rentalDetails id: " + id + " was not found");
    }
  }

  @Override
  public CarParameter save(CarParameter carParameter) {
    return carParameterDao.save(carParameter);
  }
}