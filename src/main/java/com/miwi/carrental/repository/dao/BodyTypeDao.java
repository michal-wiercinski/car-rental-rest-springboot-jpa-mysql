package com.miwi.carrental.repository.dao;

import com.miwi.carrental.domain.entity.BodyType;
import com.miwi.carrental.domain.enums.BodyTypeName;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder.In;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyTypeDao extends GenericDao<BodyType> {

  Optional<BodyType> findByTypeNameAndNumberOfSeatsAndNumberOfDoorsAndFuelTankVolumeAndVolumeOfLuggage(
      BodyTypeName bodyTypeName, Integer seats, Integer doors, Integer fuelTank, Integer luggage);

}
