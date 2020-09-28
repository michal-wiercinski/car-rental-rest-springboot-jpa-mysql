package com.miwi.carrental.control.repository;

import com.miwi.carrental.models.entity.DriveTrain;
import com.miwi.carrental.models.enums.EGearboxType;
import com.miwi.carrental.models.enums.EWheelDrive;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface DriveTrainDao extends GenericDao<DriveTrain> {

  Optional<DriveTrain> findByWheelDriveAndGearboxTypeAndNumberOfGears(EWheelDrive EWheelDrive,
      EGearboxType gearboxType, Integer numberOfGears);
}
