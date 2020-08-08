package com.miwi.carrental.control.service.car;

import com.miwi.carrental.control.dto.CarParameterDto;
import com.miwi.carrental.domain.entity.Engine;
import com.miwi.carrental.domain.enums.FuelType;
import com.miwi.carrental.control.repository.EngineDao;
import com.miwi.carrental.control.service.generic.GenericService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EngineService extends GenericService<Engine> {

  private final EngineDao engineDao;


  public EngineService(final EngineDao engineDao) {
    this.engineDao = engineDao;
  }


  public Engine getEngineFromNewCarParam(CarParameterDto carParameterDto) {
    Optional<Engine> engine = findByAllParameters(carParameterDto.getEnginePower(),
        carParameterDto.getEngineCapacity(),
        FuelType.valueOf(carParameterDto.getFuelType()),
        carParameterDto.getAverageFuelConsumption());

    if (engine.isPresent()) {
      return engine.get();
    }
    Engine newEngine = new Engine();
    newEngine.setPower(carParameterDto.getEnginePower());
    newEngine.setCapacity(carParameterDto.getEngineCapacity());
    newEngine.setFuelType(FuelType.valueOf(carParameterDto.getFuelType()));
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
        carParameterDto.getEngineCapacity(), FuelType.valueOf(carParameterDto.getFuelType().toUpperCase()),
        carParameterDto.getAverageFuelConsumption());

    if (optEngine.isEmpty()) {
      if (carParameterDto.getEnginePower() != null) {
        engine.setPower(carParameterDto.getEnginePower());
      }
      if (carParameterDto.getEngineCapacity() != null) {
        engine.setCapacity(carParameterDto.getEngineCapacity());
      }
      if (carParameterDto.getFuelType() != null) {
        engine.setFuelType(FuelType.valueOf(carParameterDto.getFuelType().toUpperCase()));
      }
      if (carParameterDto.getAverageFuelConsumption() != null) {
        engine.setFuelConsumption(carParameterDto.getAverageFuelConsumption());
      }
      return engine;
    }

    return optEngine.get();
  }
}
