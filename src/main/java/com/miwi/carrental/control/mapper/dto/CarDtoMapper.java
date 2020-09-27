package com.miwi.carrental.control.mapper.dto;

import com.miwi.carrental.control.dto.CarDto;
import com.miwi.carrental.models.entity.Car;
import com.miwi.carrental.models.enums.CarStatusType;
import com.miwi.carrental.control.mapper.generic.GenericMapper;
import com.miwi.carrental.control.service.car.CarModelService;
import com.miwi.carrental.control.service.car.CarStatusService;
import com.miwi.carrental.control.service.location.LocationService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CarDtoMapper extends GenericMapper<Car, CarDto> {

  private final CarModelService carModelService;
  private final CarParameterDtoMapper carParameterMapper;
  private final CarModelDtoMapper carModelDtoMapper;
  private final LocationDtoMapper locationDtoMapper;
  private final LocationService locationService;
  private final CarStatusService carStatusService;

  public CarDtoMapper(final CarModelService carModelService,
      final CarParameterDtoMapper carParameterMapper,
      final CarModelDtoMapper carModelDtoMapper,
      final LocationDtoMapper locationDtoMapper,
      final LocationService locationService,
      CarStatusService carStatusService) {
    this.carModelService = carModelService;
    this.carParameterMapper = carParameterMapper;
    this.carModelDtoMapper = carModelDtoMapper;
    this.locationDtoMapper = locationDtoMapper;
    this.locationService = locationService;
    this.carStatusService = carStatusService;

  }


  @Override
  public Car mapDtoToEntity(CarDto dto) {
    Car car = new Car();

    car.setRegistrationNumber(dto.getRegistrationNumber());
    car.setCarModel(carModelService.findById(dto.getCarModelDto().getId()));
    car.setLocation(locationService.findById(dto.getLocationDto().getId()));
    car.setCarParameter(carParameterMapper.mapDtoToEntity(dto.getCarParameterDto()));
    car.setCarStatus(
        carStatusService.findByCarStatusName(CarStatusType.valueOf(dto.getCarStatus())));

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
    carDto.setCarStatus(entity.getCarStatus().getCarStatusName().getType());

    return carDto;
  }

  @Override
  public List<CarDto> mapEntityListToListDto(List<Car> entityList) {
    return super.mapEntityListToListDto(entityList);
  }
}
