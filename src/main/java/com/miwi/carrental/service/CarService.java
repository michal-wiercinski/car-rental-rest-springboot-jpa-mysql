package com.miwi.carrental.service;

import com.miwi.carrental.domain.dto.CarDto;
import com.miwi.carrental.domain.entity.Car;
import com.miwi.carrental.domain.entity.CarParameter;
import com.miwi.carrental.domain.entity.CarStatus;
import com.miwi.carrental.domain.enums.CarStatusType;
import com.miwi.carrental.mapper.CarDtoMapper;
import com.miwi.carrental.repository.CarDao;
import com.miwi.carrental.service.generic.GenericService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CarService extends GenericService<Car> {

  private Logger logger = LoggerFactory.getLogger(getClass().getName());


  private final CarDao carDao;
  private final CarDtoMapper carMapper;
  private final CarParameterService carParameterService;
  private final CarStatusService carStatusService;
  private final CarModelService carModelService;
  private final LocationService locationService;

  public CarService(final CarDao carDao, final CarDtoMapper carMapper,
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

  public void changeToAvailable(Long carId, String carStatus) {
    carDao.changeToAvailable(carId, carStatus);
  }

  public Page<CarDto> getAllDtos(Pageable pageable) {

    return carMapper.mapEntityPageToPageDto(carDao.findAll(pageable));
  }

  public Optional<CarDto> getCarDtoByCarId(Long id) {
    return Optional.ofNullable(carMapper.mapEntityToDto(findById(id).get()));
  }

  public Page<CarDto> findByAvailability(Optional<String> availabilityParameter,
      Pageable pageable) {
    if (availabilityParameter.isEmpty()) {
      return carMapper.mapEntityPageToPageDto(carDao.findAll(pageable));
    }
    Optional<CarStatus> carStatus = carStatusService
        .getCarStatusFromParam(availabilityParameter.get());
    if (carStatus.isEmpty()) {
      return carMapper.mapEntityPageToPageDto(carDao.findAll(pageable));
    }
    return carMapper
        .mapEntityPageToPageDto(carDao.findAllByCarStatusLike(carStatus.get(), pageable));
  }

  public Car createNewCar(CarDto carDto) {
    Car car = carMapper.mapDtoToEntity(carDto);
    //CarParameter carParameter = carParameterService.createNewParameter(carDto);
    //car.setCarParameter(carParameter);
    logger.debug("carDto has been mapped to entity");
    save(car);
    logger.info("New car for id {} has been created ", car.getId());
    return car;
  }

  public Car editCar(Long id, CarDto carDto) {
    Car car = carDao.findById(id).orElseGet(Car::new);

    car.setId(carDto.getId());
    if (carDto.getRegistrationNumber() != null) {
      car.setRegistrationNumber(carDto.getRegistrationNumber());
    }
    if (carDto.getCarModelDto().getId() != null) {
      car.setCarModel(carModelService.findById(carDto.getCarModelDto().getId()).get());
    }
    if (carDto.getCarStatus() != null) {
      car.setCarStatus(
          carStatusService.findByCarStatusName(CarStatusType.valueOf(carDto.getCarStatus())).get());
    }
    if (carDto.getLocationDto().getId() != null) {
      car.setLocation(locationService.findById(carDto.getLocationDto().getId()).get());
    }
    carParameterService.editCarParameterByCarDto(carDto.getCarParameterDto());

    return save(car);
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
    carDao.delete(entity);
  }

  @Override
  public void deleteById(Long id) {
    carDao.deleteById(id);
  }
}
