package com.miwi.carrental.control.repository;

import com.miwi.carrental.models.entity.BodyType;
import com.miwi.carrental.models.enums.EBodyType;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyTypeDao extends GenericDao<BodyType> {

  Optional<BodyType> findByTypeNameAndNumberOfSeatsAndNumberOfDoorsAndFuelTankVolumeAndVolumeOfLuggage(
      EBodyType bodyTypeName, Integer seats, Integer doors, Integer fuelTank, Integer luggage);

  List<BodyType> findAllByTypeNameContaining(EBodyType bodyTypeName);

}
