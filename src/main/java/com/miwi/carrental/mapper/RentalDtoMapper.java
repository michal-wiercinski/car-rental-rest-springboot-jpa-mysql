package com.miwi.carrental.mapper;

import com.miwi.carrental.domain.dto.RentalDto;
import com.miwi.carrental.domain.entity.Rental;
import com.miwi.carrental.mapper.generic.GenericMapper;
import com.miwi.carrental.security.mapper.UserDtoMapper;
import org.springframework.stereotype.Component;

@Component
public class RentalDtoMapper extends GenericMapper<Rental, RentalDto> {

  private final CarDtoMapper carDtoMapper;
  private final UserDtoMapper userDtoMapper;

  public RentalDtoMapper(CarDtoMapper carDtoMapper,
      UserDtoMapper userDtoMapper) {
    this.carDtoMapper = carDtoMapper;
    this.userDtoMapper = userDtoMapper;
  }

  @Override
  public RentalDto mapEntityToDto(Rental entity) {
    RentalDto rentalDto = new RentalDto();

    rentalDto.setCarDto(carDtoMapper.mapEntityToDto(entity.getCar()));
    rentalDto.setDistance(entity.getRentalDetails().getDistance());
    rentalDto.setRentalCost(entity.getRentalDetails().getRentalCost());
    rentalDto.setStartDate(entity.getRentalDetails().getStartDate());
    rentalDto.setEndDate(entity.getRentalDetails().getEndDate());
    rentalDto.setRentalStatusType(entity.getRentalStatus().getRentalStatusType());
    rentalDto.setUserDto(userDtoMapper.mapEntityToDto(entity.getUser()));

    return rentalDto;
  }
}
