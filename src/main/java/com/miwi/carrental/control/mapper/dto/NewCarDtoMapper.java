package com.miwi.carrental.control.mapper.dto;

import com.miwi.carrental.control.dto.NewCarDto;
import com.miwi.carrental.models.entity.Car;
import com.miwi.carrental.models.enums.CarStatusType;
import com.miwi.carrental.control.service.car.CarModelService;
import com.miwi.carrental.control.service.car.CarStatusService;
import com.miwi.carrental.control.service.location.LocationService;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class NewCarDtoMapper {

  private final CarModelService carModelService;
  private final LocationService locationService;
  private final CarParameterDtoMapper carParameterDtoMapper;
  private final CarStatusService carStatusService;

  public NewCarDtoMapper(CarModelService carModelService,
      LocationService locationService,
      CarParameterDtoMapper carParameterDtoMapper,
      CarStatusService carStatusService) {
    this.carModelService = carModelService;
    this.locationService = locationService;
    this.carParameterDtoMapper = carParameterDtoMapper;
    this.carStatusService = carStatusService;
  }

  @Transactional
  public Car mapToEntity(NewCarDto newCarDto) {
    Car car = new Car();

    car.setRegistrationNumber(newCarDto.getRegistrationNumber());
    car.setCarModel(carModelService.findById(newCarDto.getCarModelId()));
    car.setLocation(locationService.findById(newCarDto.getLocationId()));
    car.setCarParameter(carParameterDtoMapper.mapDtoToEntity(newCarDto.getCarParameterDto()));
    car.setCarStatus(
        carStatusService.findByCarStatusName(CarStatusType.valueOf(newCarDto.getCarStatus())));

    return car;

  }
}
