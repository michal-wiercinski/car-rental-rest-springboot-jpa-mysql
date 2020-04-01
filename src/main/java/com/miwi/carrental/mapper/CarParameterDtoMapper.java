package com.miwi.carrental.mapper;

import com.miwi.carrental.domain.dto.CarParameterDto;
import com.miwi.carrental.domain.entity.BodyType;
import com.miwi.carrental.domain.entity.CarParameter;
import com.miwi.carrental.domain.entity.CarStatus;
import com.miwi.carrental.domain.entity.DriveTrain;
import com.miwi.carrental.domain.entity.Engine;
import com.miwi.carrental.domain.enums.CarStatusType;
import com.miwi.carrental.service.entityservice.BodyTypeService;
import com.miwi.carrental.service.entityservice.CarStatusService;
import com.miwi.carrental.service.entityservice.DriveTrainService;
import com.miwi.carrental.service.entityservice.EngineService;
import com.miwi.carrental.service.entityservice.LocationService;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class CarParameterDtoMapper {

  private final BodyTypeService bodyTypeService;
  private final EngineService engineService;
  private final DriveTrainService driveTrainService;
  private final CarStatusService carStatusService;

  public CarParameterDtoMapper(
      final BodyTypeService bodyTypeService,
      final EngineService engineService,
      final DriveTrainService driveTrainService,
      final CarStatusService carStatusService) {
    this.bodyTypeService = bodyTypeService;
    this.engineService = engineService;
    this.driveTrainService = driveTrainService;
    this.carStatusService = carStatusService;
  }

  @Transactional
  public CarParameter mapDtoToEntity(CarParameterDto carParameterDto) {
    CarParameter carParameter = new CarParameter();

    carParameter.setCurrentMileage(carParameterDto.getCurrentMileage());
    carParameter.setColor(carParameterDto.getColor());
    carParameter.setYearOfProd(carParameterDto.getYearOfProd());
    carParameter.setCarStatus(carStatusService.getCarStatusFromCarParamDto(carParameterDto));
    carParameter.setBodyType(bodyTypeService.getBodyTypeFromNewCarParam(carParameterDto));
    carParameter.setDriveTrain(driveTrainService.getDriveTrainFromNewCarParam(carParameterDto));
    carParameter.setEngine(engineService.getEngineFromNewCarParam(carParameterDto));

    return carParameter;
  }

  @Transactional
  public CarParameterDto mapEntityToDto(CarParameter carParameter) {
    CarParameterDto carParameterDto = new CarParameterDto();

    carParameterDto.setId(carParameter.getId());
    carParameterDto.setColor(carParameter.getColor());
    carParameterDto.setYearOfProd(carParameter.getYearOfProd());
    carParameterDto.setCarStatus(carParameter.getCarStatus().getCarStatusName());
    carParameterDto.setGearboxType(carParameter.getDriveTrain().getGearboxType());
    carParameterDto.setWheelDrive(carParameter.getDriveTrain().getWheelDrive());
    carParameterDto.setEnginePower(carParameter.getEngine().getPower());
    carParameterDto.setEngineCapacity(carParameter.getEngine().getCapacity());
    carParameterDto.setCurrentMileage(carParameter.getCurrentMileage());
    carParameterDto.setFuelType(carParameter.getEngine().getFuelType());
    carParameterDto.setAverageFuelConsumption(carParameter.getEngine().getFuelConsumption());
    carParameterDto.setBodyTypeName(carParameter.getBodyType().getTypeName());
    carParameterDto.setFuelTankVolume(carParameter.getBodyType().getFuelTankVolume());
    carParameterDto.setNumberOfDoors(carParameter.getBodyType().getNumberOfDoors());
    carParameterDto.setNumberOfSeats(carParameter.getBodyType().getNumberOfSeats());
    carParameterDto.setVolumeOfLuggage(carParameter.getBodyType().getVolumeOfLuggage());

    return carParameterDto;
  }
}
