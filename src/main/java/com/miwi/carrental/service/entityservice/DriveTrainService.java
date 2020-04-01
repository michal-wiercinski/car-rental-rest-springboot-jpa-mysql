package com.miwi.carrental.service.entityservice;

import com.miwi.carrental.domain.dto.CarParameterDto;
import com.miwi.carrental.domain.entity.CarParameter;
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

  public Boolean checkIfExist(WheelDrive wheelDrive, GearboxType gearboxType) {
    return findByWheelDriveAndGearbox(wheelDrive, gearboxType).isPresent();
  }
}
