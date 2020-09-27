package com.miwi.carrental.control.service.car;

import com.miwi.carrental.control.dto.CarDto;
import com.miwi.carrental.control.exception.MyResourceNotFoundException;
import com.miwi.carrental.control.mapper.dto.CarDtoMapper;
import com.miwi.carrental.control.repository.CarDao;
import com.miwi.carrental.control.service.generic.GenericService;
import com.miwi.carrental.control.service.location.LocationService;
import com.miwi.carrental.models.entity.Car;
import com.miwi.carrental.models.entity.CarStatus;
import com.miwi.carrental.models.enums.BodyTypeName;
import com.miwi.carrental.models.enums.CarStatusType;
import com.miwi.carrental.models.enums.FuelType;
import com.miwi.carrental.models.enums.GearboxType;
import com.querydsl.core.types.Predicate;
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
  private final CarParameterService carParameterService;
  private final CarStatusService carStatusService;
  private final CarModelService carModelService;
  private final LocationService locationService;

  public CarService(final CarDao carDao,
      final CarDtoMapper carMapper,
      final CarParameterService carParameterService,
      final CarStatusService carStatusService,
      CarModelService carModelService,
      LocationService locationService) {
    this.carDao = carDao;
    this.carMapper = carMapper;
    this.carParameterService = carParameterService;
    this.carStatusService = carStatusService;
    this.carModelService = carModelService;
    this.locationService = locationService;
  }

  public Page<Car> findByPredicate(Predicate predicate, Pageable pageable) {
    try {
      return checkFound(carDao.findAll(predicate, pageable));
    } catch (MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "No cars found for predicate: " + predicate);
    }
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

  public void editCar(Long id, CarDto carDto) {

    Car car = carDao.getOne(id);

    if (carDto.getRegistrationNumber() != null) {
      car.setRegistrationNumber(carDto.getRegistrationNumber());
    }
    if (carDto.getCarModelDto().getId() != null) {
      car.setCarModel(carModelService.findById((carDto.getCarModelDto().getId())));
    }
    if (carDto.getCarStatus() != null) {
      car.setCarStatus(carStatusService
          .findByCarStatusName(CarStatusType.valueOf(carDto.getCarStatus().toUpperCase())));
    }
    if (carDto.getLocationDto().getId() != null) {
      car.setLocation(locationService.findById(carDto.getLocationDto().getId()));
    }
    car.setCarParameter(carParameterService.editCarParameterByCarDto(carDto.getCarParameterDto()));
    save(car);
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
