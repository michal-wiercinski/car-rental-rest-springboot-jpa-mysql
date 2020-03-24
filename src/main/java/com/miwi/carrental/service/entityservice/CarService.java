package com.miwi.carrental.service.entityservice;

import com.miwi.carrental.domain.dto.CarDto;
import com.miwi.carrental.domain.entity.Car;
import com.miwi.carrental.domain.entity.CarParameter;
import com.miwi.carrental.mapper.CarMapper;
import com.miwi.carrental.repository.dao.CarDao;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CarService implements IGenericService<Car> {

  private Logger logger = LoggerFactory.getLogger(getClass().getName());

  private final CarDao carDao;
  private final CarMapper carMapper;
  private final CarParameterService carParameterService;
  private final CarModelService carModelService;
  private final LocationService locationService;

  public CarService(final CarDao carDao, final CarMapper carMapper,
      final CarParameterService carParameterService,
      final CarModelService carModelService,
      final LocationService locationService) {
    this.carDao = carDao;
    this.carMapper = carMapper;
    this.carParameterService = carParameterService;
    this.carModelService = carModelService;
    this.locationService = locationService;
  }

  public void changeToAvailable(Long carId, String carStatus) {
    carDao.changeToAvailable(carId, carStatus);
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
  public Optional<Car> findById(Long id) {
    return carDao.findById(id);
  }

  @Override
  public void delete(Car entity) {

  }

  @Override
  public void deleteById(Long id) {
    carDao.deleteById(id);
  }


  public Car createNewCar(CarDto carDto) {
    Car car = carMapper.mapNewCarToEntity(carDto);
    CarParameter carParameter = carParameterService.createNewParameter(carDto);
    car.setCarParameter(carParameter);
    logger.debug("carDto has been mapped to entity");
    save(car);
    logger.info("New car for id {} has been created ", car.getId());
    return car;
  }

  public Car editCar(CarDto carDto) {
    Car car = new Car();
    if (carDto.getRegistrationNumber() != null) {
      car.setRegistrationNumber(carDto.getRegistrationNumber());
    }
    if (carDto.getCarModelDtoId() != null) {
      car.setCarModel(carModelService.findById(carDto.getCarModelDtoId()).get());
    }
    if (carDto.getLocationDtoId() != null) {
      car.setCarModel(carModelService.findById(carDto.getCarModelDtoId()).get());
    }
    return car;
  }
}
