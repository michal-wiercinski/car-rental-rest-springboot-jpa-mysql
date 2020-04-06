package com.miwi.carrental.service.entityservice;

import com.miwi.carrental.domain.dto.CarDto;
import com.miwi.carrental.domain.entity.Car;
import com.miwi.carrental.domain.entity.CarParameter;
import com.miwi.carrental.domain.entity.CarStatus;
import com.miwi.carrental.mapper.CarDtoMapper;
import com.miwi.carrental.repository.dao.CarDao;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class CarService implements IGenericService<Car> {

  private Logger logger = LoggerFactory.getLogger(getClass().getName());


  private final CarDao carDao;
  private final CarDtoMapper carMapper;
  private final CarParameterService carParameterService;
  private final CarStatusService carStatusService;

  public CarService(final CarDao carDao, final CarDtoMapper carMapper,
      final CarParameterService carParameterService,
      final CarStatusService carStatusService) {
    this.carDao = carDao;
    this.carMapper = carMapper;
    this.carParameterService = carParameterService;
    this.carStatusService = carStatusService;
  }

  public void changeToAvailable(Long carId, String carStatus) {
    carDao.changeToAvailable(carId, carStatus);
  }

  public List<CarDto> findAndSortAll(Optional<String> direction,
      Optional<String> sortingAttribute) {
    Sort sort = setSort(sortingAttribute, direction);

    return carMapper.mapEntityListToListDto(carDao.findAll(sort));
  }

  public List<CarDto> findByAvailabilityAndSort(Optional<String> sortingAttribute,
      Optional<String> direction, Optional<String> availabilityParameter) {
    Sort sort = setSort(sortingAttribute, direction);
    CarStatus carStatus = carStatusService.getCarStatusFromParam(availabilityParameter);

    return carMapper.mapEntityListToListDto(carDao.findAllByCarStatusLike(sort, carStatus));
  }

  private Sort setSort(Optional<String> sortingAttribute, Optional<String> direction) {
    if (sortingAttribute.isPresent()) {
      if (direction.isPresent() && direction.get().equals(Direction.DESC)) {
        return Sort.by(sortingAttribute.get()).descending();
      }
    }
    return Sort.by(sortingAttribute.get()).ascending();
  }

  public Car createNewCar(CarDto carDto) {
    Car car = carMapper.mapDtoToEntity(carDto);
    CarParameter carParameter = carParameterService.createNewParameter(carDto);
    car.setCarParameter(carParameter);
    logger.debug("carDto has been mapped to entity");
    save(car);
    logger.info("New car for id {} has been created ", car.getId());
    return car;
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
}
