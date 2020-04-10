package com.miwi.carrental.service;

import com.miwi.carrental.domain.dto.CarParameterDto;
import com.miwi.carrental.domain.entity.DriveTrain;
import com.miwi.carrental.domain.enums.GearboxType;
import com.miwi.carrental.domain.enums.WheelDrive;
import com.miwi.carrental.repository.DriveTrainDao;
import com.miwi.carrental.service.generic.GenericService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DriveTrainService extends GenericService<DriveTrain> {

  private final DriveTrainDao driveTrainDao;

  public DriveTrainService(final DriveTrainDao driveTrainDao) {
    this.driveTrainDao = driveTrainDao;
  }

  public DriveTrain getDriveTrainFromNewCarParam(CarParameterDto carParameterDto) {
    Optional<DriveTrain> driveTrain = findByWheelDriveAndGearbox(
        WheelDrive.valueOf(carParameterDto.getWheelDrive()),
        GearboxType.valueOf(carParameterDto.getGearboxType()));

    if (driveTrain.isPresent()) {
      return driveTrain.get();
    }
    DriveTrain newDriveTrain = new DriveTrain();
    newDriveTrain.setWheelDrive(WheelDrive.valueOf(carParameterDto.getWheelDrive()));
    newDriveTrain.setGearboxType(GearboxType.valueOf(carParameterDto.getGearboxType()));

    return newDriveTrain;
  }

  public Optional<DriveTrain> findByWheelDriveAndGearbox(WheelDrive wheelDrive,
      GearboxType gearboxType) {
    return driveTrainDao.findByWheelDriveAndGearboxType(wheelDrive, gearboxType);
  }

  public DriveTrain editDriveTrainByDto(CarParameterDto carParameterDto) {
    DriveTrain driveTrain = new DriveTrain();
    Optional<DriveTrain> optDriveTrain = findByWheelDriveAndGearbox(WheelDrive.valueOf(carParameterDto.getWheelDrive()),
        GearboxType.valueOf(carParameterDto.getGearboxType()));
    if (optDriveTrain.isEmpty()) {
      if (carParameterDto.getWheelDrive() != null) {
        driveTrain.setWheelDrive(WheelDrive.valueOf(carParameterDto.getWheelDrive()));
      }
      if (carParameterDto.getGearboxType() != null) {
        driveTrain.setGearboxType(GearboxType.valueOf(carParameterDto.getGearboxType()));
      }
      return driveTrain;
    }

    return optDriveTrain.get();
  }
}
