package com.miwi.carrental.service.entityservice;

import com.miwi.carrental.domain.dto.CarDto;
import com.miwi.carrental.domain.entity.CarParameter;
import com.miwi.carrental.mapper.CarParameterDtoMapper;
import com.miwi.carrental.repository.dao.CarParameterDao;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CarParameterService {

  private final CarParameterDao carParameterDao;
  private final BodyTypeService bodyTypeService;
  private final CarStatusService carStatusService;
  private final CarParameterDtoMapper carParameterDtoMapper;

  public CarParameterService(final CarParameterDao carParameterDao,
      final BodyTypeService bodyTypeService,
      final CarStatusService carStatusService,
      final CarParameterDtoMapper carParameterDtoMapper) {
    this.carParameterDao = carParameterDao;
    this.bodyTypeService = bodyTypeService;
    this.carStatusService = carStatusService;
    this.carParameterDtoMapper = carParameterDtoMapper;
  }

  @Transactional
  public CarParameter save(CarParameter carParameter) {
    return carParameterDao.save(carParameter);
  }

  public CarParameter createNewParameter(CarDto carDto) {
    return carParameterDtoMapper.mapDtoToEntity(carDto.getCarParameterDto());
  }
}