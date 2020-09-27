package com.miwi.carrental.control.service.car;

import com.miwi.carrental.control.repository.DriveTrainDao;
import com.miwi.carrental.control.service.generic.GenericService;
import com.miwi.carrental.control.dto.CarParameterDto;
import com.miwi.carrental.models.entity.DriveTrain;
import com.miwi.carrental.models.enums.GearboxType;
import com.miwi.carrental.models.enums.WheelDrive;
import com.miwi.carrental.control.exception.MyResourceNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DriveTrainService extends GenericService<DriveTrain> {

  private final DriveTrainDao driveTrainDao;

  public DriveTrainService(final DriveTrainDao driveTrainDao) {
    this.driveTrainDao = driveTrainDao;
  }

  public DriveTrain getOrCreateByDto(CarParameterDto carParameterDto) {
    Optional<DriveTrain> driveTrain = findByWheelDriveAndGearbox(
        WheelDrive.valueOf(carParameterDto.getWheelDrive()),
        GearboxType.valueOf(carParameterDto.getGearboxType()),
        carParameterDto.getNumberOfGears());

    if (driveTrain.isPresent()) {
      return driveTrain.get();
    }
    DriveTrain newDriveTrain = new DriveTrain();
    newDriveTrain.setWheelDrive(WheelDrive.valueOf(carParameterDto.getWheelDrive()));
    newDriveTrain.setGearboxType(GearboxType.valueOf(carParameterDto.getGearboxType()));
    newDriveTrain.setNumberOfGears(carParameterDto.getNumberOfGears());

    return newDriveTrain;
  }

  private Optional<DriveTrain> findByWheelDriveAndGearbox(WheelDrive wheelDrive,
      GearboxType gearboxType, Integer numberOfGears) {
    return driveTrainDao
        .findByWheelDriveAndGearboxTypeAndNumberOfGears(wheelDrive, gearboxType, numberOfGears);
  }

  public DriveTrain editDriveTrainByDto(CarParameterDto carParameterDto) {
    DriveTrain driveTrain = new DriveTrain();
    String wheelDriveName = carParameterDto.getWheelDrive().toUpperCase().replace("-", "_")
        .replace(" ", "_");
    Optional<DriveTrain> optDriveTrain = findByWheelDriveAndGearbox(
        WheelDrive.valueOf(wheelDriveName),
        GearboxType.valueOf(carParameterDto.getGearboxType().toUpperCase()),
        carParameterDto.getNumberOfGears());

    if (optDriveTrain.isEmpty()) {
      if (carParameterDto.getWheelDrive() != null) {
        driveTrain.setWheelDrive(WheelDrive.valueOf(wheelDriveName));
      }
      if (carParameterDto.getGearboxType() != null) {
        driveTrain
            .setGearboxType(GearboxType.valueOf(carParameterDto.getGearboxType().toUpperCase()));
      }
      return driveTrain;
    }

    return optDriveTrain.get();
  }

  @Override
  public List<DriveTrain> findAll() {
    try {
      return checkFound(driveTrainDao.findAll());
    } catch (
        MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List of drive trains not found", ex);
    }
  }
}