package com.miwi.carrental.service;

import com.miwi.carrental.domain.dto.CarParameterDto;
import com.miwi.carrental.domain.entity.BodyType;
import com.miwi.carrental.domain.enums.BodyTypeName;
import com.miwi.carrental.repository.BodyTypeDao;
import com.miwi.carrental.service.generic.GenericService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class BodyTypeService extends GenericService<BodyType> {

  private final BodyTypeDao bodyTypeDao;

  public BodyTypeService(BodyTypeDao bodyTypeDao) {
    this.bodyTypeDao = bodyTypeDao;
  }

  public Optional<BodyType> findByAllParameters(BodyTypeName bodyTypeName, Integer seats,
      Integer doors, Integer fuelTank, Integer luggage) {
    return bodyTypeDao
        .findByTypeNameAndNumberOfSeatsAndNumberOfDoorsAndFuelTankVolumeAndVolumeOfLuggage(
            bodyTypeName, seats, doors, fuelTank, luggage);
  }

  public BodyType getBodyTypeFromNewCarParam(CarParameterDto carParameterDto) {
    Optional<BodyType> bodyType = findByAllParameters(carParameterDto.getBodyTypeName(),
        carParameterDto.getNumberOfSeats(),
        carParameterDto.getNumberOfDoors(), carParameterDto.getFuelTankVolume(),
        carParameterDto.getVolumeOfLuggage());

    if (bodyType.isPresent()) {
      return bodyType.get();
    }

    BodyType newBodyType = new BodyType();
    newBodyType.setTypeName(carParameterDto.getBodyTypeName());
    newBodyType.setFuelTankVolume(carParameterDto.getFuelTankVolume());
    newBodyType.setNumberOfDoors(carParameterDto.getNumberOfDoors());
    newBodyType.setNumberOfSeats(carParameterDto.getNumberOfSeats());
    newBodyType.setVolumeOfLuggage(carParameterDto.getVolumeOfLuggage());

    return newBodyType;
  }

  public BodyType editBodyTypeByDto(CarParameterDto carParameterDto) {
    BodyType bodyType = new BodyType();
    Optional<BodyType> optBodyType = findByAllParameters(carParameterDto.getBodyTypeName(),
        carParameterDto.getNumberOfSeats(), carParameterDto.getNumberOfDoors(),
        carParameterDto.getFuelTankVolume(), carParameterDto.getVolumeOfLuggage());

    if (optBodyType.isEmpty()) {
      if (carParameterDto.getBodyTypeName() != null) {
        bodyType.setTypeName(carParameterDto.getBodyTypeName());
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
    return bodyTypeDao.findAll();
  }

  @Override
  public Optional<BodyType> findById(Long id) {
    return bodyTypeDao.findById(id);
  }
}
