package com.miwi.carrental.control.service.car;

import com.miwi.carrental.control.dto.CarParameterDto;
import com.miwi.carrental.models.entity.BodyType;
import com.miwi.carrental.models.enums.EBodyType;
import com.miwi.carrental.control.exception.MyResourceNotFoundException;
import com.miwi.carrental.control.repository.BodyTypeDao;
import com.miwi.carrental.control.service.generic.GenericService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BodyTypeService extends GenericService<BodyType> {

  private Logger logger = LoggerFactory.getLogger(getClass().getName());

  private final BodyTypeDao bodyTypeDao;

  public BodyTypeService(BodyTypeDao bodyTypeDao) {
    this.bodyTypeDao = bodyTypeDao;
  }

  public Optional<BodyType> findByAllParameters(EBodyType bodyTypeName, Integer seats,
      Integer doors, Integer fuelTank, Integer luggage) {
    return bodyTypeDao
        .findByTypeNameAndNumberOfSeatsAndNumberOfDoorsAndFuelTankVolumeAndVolumeOfLuggage(
            bodyTypeName, seats, doors, fuelTank, luggage);
  }

  public BodyType getBodyTypeFromNewCarParam(CarParameterDto carParameterDto) {
    Optional<BodyType> bodyType = findByAllParameters(
        EBodyType.valueOf(carParameterDto.getBodyTypeName()),
        carParameterDto.getNumberOfSeats(),
        carParameterDto.getNumberOfDoors(), carParameterDto.getFuelTankVolume(),
        carParameterDto.getVolumeOfLuggage());

    if (bodyType.isPresent()) {
      return bodyType.get();
    }

    BodyType newBodyType = new BodyType();
    newBodyType.setTypeName(EBodyType.valueOf(carParameterDto.getBodyTypeName()));
    newBodyType.setFuelTankVolume(carParameterDto.getFuelTankVolume());
    newBodyType.setNumberOfDoors(carParameterDto.getNumberOfDoors());
    newBodyType.setNumberOfSeats(carParameterDto.getNumberOfSeats());
    newBodyType.setVolumeOfLuggage(carParameterDto.getVolumeOfLuggage());

    return newBodyType;
  }

  public BodyType editBodyTypeByDto(CarParameterDto carParameterDto) {
    BodyType bodyType = new BodyType();
    Optional<BodyType> optBodyType = findByAllParameters(
        EBodyType.valueOf(carParameterDto.getBodyTypeName().toUpperCase()),
        carParameterDto.getNumberOfSeats(), carParameterDto.getNumberOfDoors(),
        carParameterDto.getFuelTankVolume(), carParameterDto.getVolumeOfLuggage());

    if (optBodyType.isEmpty()) {
      if (carParameterDto.getBodyTypeName() != null) {
        bodyType.setTypeName(EBodyType.valueOf(carParameterDto.getBodyTypeName().toUpperCase()));
      }
      if (carParameterDto.getNumberOfSeats() != null) {
        bodyType.setNumberOfSeats(carParameterDto.getNumberOfSeats());
      }
      if (carParameterDto.getNumberOfDoors() != null) {
        bodyType.setNumberOfDoors(carParameterDto.getNumberOfDoors());
      }
      if (carParameterDto.getFuelTankVolume() != null) {
        bodyType.setFuelTankVolume(carParameterDto.getFuelTankVolume());
      }
      if (carParameterDto.getVolumeOfLuggage() != null) {
        bodyType.setVolumeOfLuggage(carParameterDto.getVolumeOfLuggage());
      }
      return bodyType;
    }

    return optBodyType.get();
  }

  @Override
  public List<BodyType> findAll() {
    try {
      return checkFound(bodyTypeDao.findAll());
    } catch (
        MyResourceNotFoundException ex) {
      logger.warn("Cars page is empty");
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page with cars not found", ex);
    }

  }

  @Override
  public BodyType findById(Long id) {
    try {
      return checkFound(bodyTypeDao.findById(id));
    } catch (MyResourceNotFoundException ex) {
      logger.warn("The body type with id: {} was not found ", id);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The body type with id: " + id + " was not found",
          ex);
    }
  }
}

