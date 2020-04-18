package com.miwi.carrental.mapper.dto;

import com.miwi.carrental.domain.dto.CarModelDto;
import com.miwi.carrental.domain.entity.CarModel;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class CarModelDtoMapper {

  @Transactional
  public CarModel mapDtoToEntity(CarModelDto carModelDto) {
    CarModel carModel = new CarModel();

    carModel.setName(carModelDto.getCarModelName());
    carModel.getBrand().setName(carModelDto.getBrandName());

    return carModel;
  }

  @Transactional
  public CarModelDto mapEntityToDto(CarModel carModel) {
    CarModelDto carModelDto = new CarModelDto();

    carModelDto.setId(carModel.getId());
    carModelDto.setCarModelName(carModel.getName());
    carModelDto.setBrandName(carModel.getBrand().getName());

    return carModelDto;
  }
}
