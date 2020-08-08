package com.miwi.carrental.control.mapper.dto;

import com.miwi.carrental.control.dto.CarParameterDto;
import com.miwi.carrental.domain.entity.CarParameter;
import com.miwi.carrental.control.service.car.BodyTypeService;
import com.miwi.carrental.control.service.car.DriveTrainService;
import com.miwi.carrental.control.service.car.EngineService;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class CarParameterDtoMapper {

  private final BodyTypeService bodyTypeService;
  private final EngineService engineService;
  private final DriveTrainService driveTrainService;

  public CarParameterDtoMapper(
      final BodyTypeService bodyTypeService,
      final EngineService engineService,
      final DriveTrainService driveTrainService) {
    this.bodyTypeService = bodyTypeService;
    this.engineService = engineService;
    this.driveTrainService = driveTrainService;
  }

  @Transactional
  public CarParameter mapDtoToEntity(CarParameterDto carParameterDto) {
    CarParameter carParameter = new CarParameter();

    carParameter.setCurrentMileage(carParameterDto.getCurrentMileage());
    carParameter.setColor(carParameterDto.getColor());
    carParameter.setYearOfProd(carParameterDto.getYearOfProd());
    carParameter.setDailyRate(carParameterDto.getDailyRate());
    carParameter.setBodyType(bodyTypeService.getBodyTypeFromNewCarParam(carParameterDto));
    carParameter.setDriveTrain(driveTrainService.getOrCreateByDto(carParameterDto));
    carParameter.setEngine(engineService.getEngineFromNewCarParam(carParameterDto));

    return carParameter;
  }

  @Transactional
  public CarParameterDto mapEntityToDto(CarParameter carParameter) {
    CarParameterDto carParameterDto = new CarParameterDto();

    carParameterDto.setId(carParameter.getId());
    carParameterDto.setColor(carParameter.getColor());
    carParameterDto.setYearOfProd(carParameter.getYearOfProd());
    carParameterDto.setDailyRate(carParameter.getDailyRate());
    carParameterDto.setGearboxType(carParameter.getDriveTrain().getGearboxType().getType());
    carParameterDto.setNumberOfGears(carParameter.getDriveTrain().getNumberOfGears());
    carParameterDto.setWheelDrive(carParameter.getDriveTrain().getWheelDrive().getType());
    carParameterDto.setEnginePower(carParameter.getEngine().getPower());
    carParameterDto.setEngineCapacity(carParameter.getEngine().getCapacity());
    carParameterDto.setCurrentMileage(carParameter.getCurrentMileage());
    carParameterDto.setFuelType(carParameter.getEngine().getFuelType().getType());
    carParameterDto.setAverageFuelConsumption(carParameter.getEngine().getFuelConsumption());
    carParameterDto.setBodyTypeName(carParameter.getBodyType().getTypeName().getType());
    carParameterDto.setFuelTankVolume(carParameter.getBodyType().getFuelTankVolume());
    carParameterDto.setNumberOfDoors(carParameter.getBodyType().getNumberOfDoors());
    carParameterDto.setNumberOfSeats(carParameter.getBodyType().getNumberOfSeats());
    carParameterDto.setVolumeOfLuggage(carParameter.getBodyType().getVolumeOfLuggage());

    return carParameterDto;
  }
}
