package com.miwi.carrental.service.entityservice;

import com.miwi.carrental.domain.dto.CarParameterDto;
import com.miwi.carrental.domain.entity.Engine;
import com.miwi.carrental.domain.enums.FuelType;
import com.miwi.carrental.repository.dao.EngineDao;
import java.lang.reflect.Field;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EngineService {

  private final EngineDao engineDao;


  public EngineService(final EngineDao engineDao) {
    this.engineDao = engineDao;
  }


  public Engine getEngineFromNewCarParam(CarParameterDto carParameterDto) {
    Optional<Engine> engine = findByAllParameters(carParameterDto.getEnginePower(),
        carParameterDto.getEngineCapacity(),
        carParameterDto.getFuelType(), carParameterDto.getAverageFuelConsumption());

    if (engine.isPresent()) {
      return engine.get();
    }
    Engine newEngine = new Engine();
    newEngine.setPower(carParameterDto.getEnginePower());
    newEngine.setCapacity(carParameterDto.getEngineCapacity());
    newEngine.setFuelType(carParameterDto.getFuelType());
    newEngine.setFuelConsumption(carParameterDto.getAverageFuelConsumption());

    return newEngine;
  }

  public Optional<Engine> findByAllParameters(Integer power,
      Integer capacity, FuelType fuelType, Double fuelConsumption) {
    return engineDao.findByPowerAndCapacityAndFuelTypeAndFuelConsumption(power, capacity, fuelType,
        fuelConsumption);
  }

  public Engine editEngineByDto(CarParameterDto carParameterDto) {
    Engine engine = new Engine();
    Optional<Engine> optEngine;
    optEngine = findByAllParameters(carParameterDto.getEnginePower(),
        carParameterDto.getEngineCapacity(), carParameterDto.getFuelType(),
        carParameterDto.getAverageFuelConsumption());

    if (optEngine.isEmpty()) {
      if (carParameterDto.getEnginePower() != null) {
        engine.setPower(carParameterDto.getEnginePower());
      }
      if (carParameterDto.getEngineCapacity() != null) {
        engine.setCapacity(carParameterDto.getEngineCapacity());
      }
      if (carParameterDto.getFuelType() != null) {
        engine.setFuelType(carParameterDto.getFuelType());
      }
      if (carParameterDto.getAverageFuelConsumption() != null) {
        engine.setFuelConsumption(carParameterDto.getAverageFuelConsumption());
      }
      return engine;
    }

    return optEngine.get();
  }
}
