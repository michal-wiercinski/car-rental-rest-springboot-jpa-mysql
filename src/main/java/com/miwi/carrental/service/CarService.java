package com.miwi.carrental.service;

import com.miwi.carrental.domain.dto.NewCarDto;
import com.miwi.carrental.domain.dto.CarDto;
import com.miwi.carrental.domain.entity.Car;
import com.miwi.carrental.domain.entity.CarStatus;
import com.miwi.carrental.domain.enums.BodyTypeName;
import com.miwi.carrental.domain.enums.CarStatusType;
import com.miwi.carrental.domain.enums.FuelType;
import com.miwi.carrental.domain.enums.GearboxType;
import com.miwi.carrental.exception.MyResourceNotFoundException;
import com.miwi.carrental.mapper.dto.CarDtoMapper;
import com.miwi.carrental.mapper.dto.NewCarDtoMapper;
import com.miwi.carrental.repository.CarDao;
import com.miwi.carrental.service.generic.GenericService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CarService extends GenericService<Car> {

  private Logger logger = LoggerFactory.getLogger(getClass().getName());

  private final CarDao carDao;

  private final CarDtoMapper carMapper;
  private final NewCarDtoMapper newCarMapper;
  private final CarParameterService carParameterService;
  private final CarStatusService carStatusService;
  private final CarModelService carModelService;
  private final LocationService locationService;

  public CarService(final CarDao carDao, final CarDtoMapper carMapper,
      NewCarDtoMapper newCarMapper,
      final CarParameterService carParameterService,
      final CarStatusService carStatusService,
      CarModelService carModelService,
      LocationService locationService) {
    this.carDao = carDao;
    this.carMapper = carMapper;
    this.newCarMapper = newCarMapper;
    this.carParameterService = carParameterService;
    this.carStatusService = carStatusService;
    this.carModelService = carModelService;
    this.locationService = locationService;
  }

  public void changeToAvailable(Long carId, String carStatus) {
    carDao.changeToAvailable(carId, carStatus);
  }

  public Page<Car> findByBodyTypeName(String typeNameParam, Pageable pageable) {
    try {
      return checkFound(carDao
          .findAllByCarParameter_BodyTypeTypeName(BodyTypeName.valueOf(typeNameParam), pageable));
    } catch (MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "No cars found for body type name: " + typeNameParam);
    }
  }

  public Page<Car> findByGearboxType(String typeNameParam, Pageable pageable) {
    try {
      return checkFound(carDao
          .findAllByCarParameter_DriveTrainGearboxType(GearboxType.valueOf(typeNameParam),
              pageable));
    } catch (MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "No cars found for gearbox type name: " + typeNameParam);
    }
  }

  public Page<Car> findByFuelType(String typeNameParam, Pageable pageable) {
    try {
      return checkFound(carDao
          .findAllByCarParameter_EngineFuelType(FuelType.valueOf(typeNameParam), pageable));
    } catch (MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "No cars found for fuel type name: " + typeNameParam);
    }
  }

  public Page<Car> findByAvailability(String availabilityParameter,
      Pageable pageable) {
    if (validCarStatusParam(availabilityParameter)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad availability parameter");
    }
    try {
      CarStatus carStatus = carStatusService
          .findByCarStatusName(CarStatusType.valueOf(availabilityParameter));
      return checkFound(carDao.findAllByCarStatusLike(carStatus, pageable));
    } catch (MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "No cars found for status: " + availabilityParameter);
    }

  }

  public Car createNewCar(CarDto newCarDto) {
    Car car = carMapper.mapDtoToEntity(newCarDto);
    logger.debug("newCarDto has been mapped to entity");
    save(car);
    logger.info("New car for id {} has been created ", car.getId());
    return car;
  }

  public Car editCar(Long id, CarDto carDto) {
    try {
      Car car = carDao.findById(id).orElseGet(Car::new);

      car.setId(carDto.getId());
      if (carDto.getRegistrationNumber() != null) {
        car.setRegistrationNumber(carDto.getRegistrationNumber());
      }
      if (carDto.getCarModelDto().getId() != null) {
        car.setCarModel(carModelService.findById((carDto.getCarModelDto().getId())));
      }
      if (carDto.getCarStatus() != null) {
        car.setCarStatus(carStatusService
            .findByCarStatusName(CarStatusType.valueOf(carDto.getCarStatus())));
      }
      if (carDto.getLocationDto().getId() != null) {
        car.setLocation(locationService.findById(carDto.getLocationDto().getId()));
      }
      carParameterService.editCarParameterByCarDto(carDto.getCarParameterDto());
      return save(car);
    } catch (IllegalArgumentException iae) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad argument", iae);
    }
  }

  @Override
  public Car findById(Long id) {
    try {
      return checkFound(carDao.findById(id));
    } catch (MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The car with id: " + id + " was not found");
    }
  }

  @Override
  public Page<Car> findAll(Pageable pageable) {
    try {
      return checkFound(carDao.findAll(pageable));
    } catch (MyResourceNotFoundException ex) {
      logger.warn("Cars page is empty");
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page with cars not found", ex);
    }
  }

  @Override
  public List<Car> findAll() {
    return carDao.findAll();
  }

  @Override
  public Car save(Car entity) {
    return carDao.save(entity);
  }

  @Override
  public void deleteById(Long id) {
    carDao.delete(findById(id));
  }

  private boolean validCarStatusParam(String param) {
    if (param.isEmpty()) {
      return false;
    }
    return param.equals(CarStatusType.AVAILABLE.getType()) ||
        param.equals(CarStatusType.UNAVAILABLE.getType());
  }
}
