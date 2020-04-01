package com.miwi.carrental.repository.dao;

import com.miwi.carrental.domain.entity.DriveTrain;
import com.miwi.carrental.domain.enums.GearboxType;
import com.miwi.carrental.domain.enums.WheelDrive;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface DriveTrainDao extends GenericDao<DriveTrain> {

  Optional<DriveTrain> findByWheelDriveAndGearboxType(WheelDrive wheelDrive,
      GearboxType gearboxType);
}
