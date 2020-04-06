package com.miwi.carrental.mapper;

import com.miwi.carrental.domain.dto.CarDto;
import com.miwi.carrental.domain.entity.Car;
import com.miwi.carrental.service.entityservice.CarModelService;
import com.miwi.carrental.service.entityservice.CarStatusService;
import com.miwi.carrental.service.entityservice.LocationService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CarDtoMapper extends GenericMapper<Car, CarDto> {

  private final CarModelService carModelService;
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
    this.carParameterMapper = carParameterMapper;
    this.carModelDtoMapper = carModelDtoMapper;
    this.locationDtoMapper = locationDtoMapper;
    this.carStatusService = carStatusService;
  }

  @Override
  public Car mapDtoToEntity(CarDto dto) {
    Car car = new Car();

    car.setRegistrationNumber(dto.getRegistrationNumber());
    car.setCarModel(carModelService.findByName(dto.getCarModelDto().getCarModelName()));
    car.setLocation(locationDtoMapper.mapDtoToEntity(dto.getLocationDto()));
    car.setCarParameter(carParameterMapper.mapDtoToEntity(dto.getCarParameterDto()));
    car.setCarStatus(carStatusService.getCarStatusFromCarParamDto(dto));

    return car;
  }

  @Override
  public CarDto mapEntityToDto(Car entity) {
    CarDto carDto = new CarDto();

    carDto.setId(entity.getId());
    carDto.setRegistrationNumber(entity.getRegistrationNumber());
    carDto.setCarModelDto(carModelDtoMapper.mapEntityToDto(entity.getCarModel()));
    carDto.setCarParameterDto(carParameterMapper.mapEntityToDto(entity.getCarParameter()));
    carDto.setLocationDto(locationDtoMapper.mapEntityToDto(entity.getLocation()));
    carDto.setCarStatus(entity.getCarStatus().getCarStatusName());

    return carDto;
  }

  @Override
  public List<CarDto> mapEntityListToListDto(List<Car> entityList) {
    return super.mapEntityListToListDto(entityList);
  }
}
