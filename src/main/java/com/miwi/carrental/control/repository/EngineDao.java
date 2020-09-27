package com.miwi.carrental.control.repository;

import com.miwi.carrental.models.entity.Engine;
import com.miwi.carrental.models.enums.FuelType;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface EngineDao extends GenericDao<Engine> {

  Optional<Engine> findByPowerAndCapacityAndFuelTypeAndFuelConsumption(Integer power,
      Integer capacity, FuelType fuelType, Double fuelConsumption);

}
