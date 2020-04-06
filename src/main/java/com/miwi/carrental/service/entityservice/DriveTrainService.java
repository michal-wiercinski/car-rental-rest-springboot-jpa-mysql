package com.miwi.carrental.service.entityservice;

import com.miwi.carrental.domain.dto.CarParameterDto;
import com.miwi.carrental.domain.entity.DriveTrain;
import com.miwi.carrental.domain.enums.GearboxType;
import com.miwi.carrental.domain.enums.WheelDrive;
import com.miwi.carrental.repository.dao.DriveTrainDao;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DriveTrainService {

  private final DriveTrainDao driveTrainDao;

  public DriveTrainService(final DriveTrainDao driveTrainDao) {
    this.driveTrainDao = driveTrainDao;
  }

  public DriveTrain getDriveTrainFromNewCarParam(CarParameterDto carParameterDto) {
    Optional<DriveTrain> driveTrain = findByWheelDriveAndGearbox(carParameterDto.getWheelDrive(),
        carParameterDto.getGearboxType());

    if (driveTrain.isPresent()) {
      return driveTrain.get();
    }
    DriveTrain newDriveTrain = new DriveTrain();
    newDriveTrain.setWheelDrive(carParameterDto.getWheelDrive());
    newDriveTrain.setGearboxType(carParameterDto.getGearboxType());

    return newDriveTrain;
  }

  public Optional<DriveTrain> findByWheelDriveAndGearbox(WheelDrive wheelDrive,
      GearboxType gearboxType) {
    return driveTrainDao.findByWheelDriveAndGearboxType(wheelDrive, gearboxType);
  }

  public DriveTrain editDriveTrainByDto(CarParameterDto carParameterDto) {
    DriveTrain driveTrain = new DriveTrain();
    Optional<DriveTrain> optDriveTrain = findByWheelDriveAndGearbox(carParameterDto.getWheelDrive(),
        carParameterDto.getGearboxType());
    if (optDriveTrain.isEmpty()) {
      if (carParameterDto.getWheelDrive() != null) {
        driveTrain.setWheelDrive(carParameterDto.getWheelDrive());
      }
      if (carParameterDto.getGearboxType() != null) {
        driveTrain.setWheelDrive(carParameterDto.getWheelDrive());
      }
      return driveTrain;
    }

    return optDriveTrain.get();
  }
}
