package com.miwi.carrental.control.repository;

import com.miwi.carrental.models.entity.DriveTrain;
import com.miwi.carrental.models.enums.GearboxType;
import com.miwi.carrental.models.enums.WheelDrive;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface DriveTrainDao extends GenericDao<DriveTrain> {

  Optional<DriveTrain> findByWheelDriveAndGearboxTypeAndNumberOfGears(WheelDrive wheelDrive,
      GearboxType gearboxType, Integer numberOfGears);
}
