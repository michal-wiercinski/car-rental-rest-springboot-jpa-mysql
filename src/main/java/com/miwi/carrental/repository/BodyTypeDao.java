package com.miwi.carrental.repository;

import com.miwi.carrental.domain.entity.BodyType;
import com.miwi.carrental.domain.enums.BodyTypeName;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyTypeDao extends GenericDao<BodyType> {

  Optional<BodyType> findByTypeNameAndNumberOfSeatsAndNumberOfDoorsAndFuelTankVolumeAndVolumeOfLuggage(
      BodyTypeName bodyTypeName, Integer seats, Integer doors, Integer fuelTank, Integer luggage);

}
