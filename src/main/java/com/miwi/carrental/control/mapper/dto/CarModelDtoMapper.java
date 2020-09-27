package com.miwi.carrental.control.mapper.dto;

import com.miwi.carrental.control.dto.CarModelDto;
import com.miwi.carrental.models.entity.CarModel;
import com.miwi.carrental.control.mapper.generic.GenericMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class CarModelDtoMapper extends GenericMapper<CarModel, CarModelDto> {

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
