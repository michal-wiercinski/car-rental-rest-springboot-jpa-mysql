package com.miwi.carrental.control.repository;

import com.miwi.carrental.domain.entity.Engine;
import com.miwi.carrental.domain.enums.FuelType;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface EngineDao extends GenericDao<Engine> {

  Optional<Engine> findByPowerAndCapacityAndFuelTypeAndFuelConsumption(Integer power,
      Integer capacity, FuelType fuelType, Double fuelConsumption);

}
