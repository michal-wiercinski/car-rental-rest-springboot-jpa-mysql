package com.miwi.carrental.service.entityservice;

import com.miwi.carrental.domain.dto.CarParameterDto;
import com.miwi.carrental.domain.entity.BodyType;
import com.miwi.carrental.domain.entity.CarParameter;
import com.miwi.carrental.domain.enums.BodyTypeName;
import com.miwi.carrental.repository.dao.BodyTypeDao;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class BodyTypeService implements IGenericService<BodyType> {

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


  @Override
  public List<BodyType> findAll() {
    return bodyTypeDao.findAll();
  }

  @Override
  public BodyType save(BodyType entity) {
    return null;
  }

  @Override
  public Optional<BodyType> findById(Long id) {
    return bodyTypeDao.findById(id);
  }

  @Override
  public void delete(BodyType entity) {

  }

  @Override
  public void deleteById(Long id) {

  }
}
