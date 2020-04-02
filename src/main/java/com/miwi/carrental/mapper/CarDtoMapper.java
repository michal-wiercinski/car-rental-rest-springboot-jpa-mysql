package com.miwi.carrental.mapper;

import com.miwi.carrental.domain.dto.CarDto;
import com.miwi.carrental.domain.entity.Car;
import com.miwi.carrental.service.entityservice.CarModelService;
import com.miwi.carrental.service.entityservice.CarStatusService;
import com.miwi.carrental.service.entityservice.LocationService;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class CarDtoMapper {

  private final CarModelService carModelService;
  private final LocationService locationService;
  private final CarParameterDtoMapper carParameterMapper;
  private final CarModelDtoMapper carModelDtoMapper;
  private final LocationDtoMapper locationDtoMapper;
  private final CarStatusService carStatusService;

  public CarDtoMapper(final CarModelService carModelService,
      final LocationService locationService,
      final CarParameterDtoMapper carParameterMapper,
      final CarModelDtoMapper carModelDtoMapper,
      final LocationDtoMapper locationDtoMapper,
      CarStatusService carStatusService) {
    this.carModelService = carModelService;
    this.locationService = locationService;
    this.carParameterMapper = carParameterMapper;
    this.carModelDtoMapper = carModelDtoMapper;
    this.locationDtoMapper = locationDtoMapper;
    this.carStatusService = carStatusService;
  }

  @Transactional
  public Car mapDtoToEntity(CarDto carDto) {
    Car car = new Car();

    car.setRegistrationNumber(carDto.getRegistrationNumber());
    car.setCarModel(carModelService.findByName(carDto.getCarModelDto().getCarModelName()));
    car.setLocation(locationDtoMapper.mapDtoToEntity(carDto.getLocationDto()));
    car.setCarParameter(carParameterMapper.mapDtoToEntity(carDto.getCarParameterDto()));
    car.setCarStatus(carStatusService.getCarStatusFromCarParamDto(carDto));

    return car;
  }

  @Transactional
  public CarDto mapEntityToDto(Car car) {
    CarDto carDto = new CarDto();

    carDto.setId(car.getId());
    carDto.setRegistrationNumber(car.getRegistrationNumber());
    carDto.setCarModelDto(carModelDtoMapper.mapEntityToDto(car.getCarModel()));
    carDto.setCarParameterDto(carParameterMapper.mapEntityToDto(car.getCarParameter()));
    carDto.setLocationDto(locationDtoMapper.mapEntityToDto(car.getLocation()));
    carDto.setCarStatus(car.getCarStatus().getCarStatusName());

    return carDto;
  }

}
