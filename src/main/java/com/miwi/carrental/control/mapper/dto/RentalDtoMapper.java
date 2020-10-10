package com.miwi.carrental.control.mapper.dto;

import com.miwi.carrental.control.dto.RentalDto;
import com.miwi.carrental.control.mapper.generic.GenericMapper;
import com.miwi.carrental.control.service.rent.RentalDetailsService;
import com.miwi.carrental.control.service.rent.RentalStatusService;
import com.miwi.carrental.models.entity.Rental;
import org.springframework.stereotype.Component;

@Component
public class RentalDtoMapper extends GenericMapper<Rental, RentalDto> {

  private final CarDtoMapper carDtoMapper;
  private final UserDtoMapper userDtoMapper;
  private final RentalDetailsService rentalDetailsService;
  private final RentalStatusService rentalStatusService;

  public RentalDtoMapper(final CarDtoMapper carDtoMapper,
      final UserDtoMapper userDtoMapper,
      final RentalDetailsService rentalDetailsService,
      final RentalStatusService rentalStatusService) {
    this.rentalDetailsService = rentalDetailsService;
    this.carDtoMapper = carDtoMapper;
    this.userDtoMapper = userDtoMapper;
    this.rentalStatusService = rentalStatusService;
  }

  @Override
  public Rental mapDtoToEntity(RentalDto dto) {
    Rental rental = new Rental();

    rental.setRentalDetails(rentalDetailsService.createRentalDetails(dto));
    /*rental.setRentalStatus(
        rentalStatusService.findByStatus(ERentalStatus.valueOf(dto.getRentalStatus())));
*/
    return rental;
  }

  @Override
  public RentalDto mapEntityToDto(Rental entity) {
    RentalDto rentalDto = new RentalDto();

    rentalDto.setCarDto(carDtoMapper.mapEntityToDto(entity.getCar()));
    rentalDto.setDistance(entity.getRentalDetails().getDistance());
    rentalDto.setRentalCost(entity.getRentalDetails().getRentalCost());
    rentalDto.setStartDate(entity.getRentalDetails().getStartDate());
    rentalDto.setEndDate(entity.getRentalDetails().getEndDate());
    rentalDto.setRentalStatus(entity.getRentalStatus().getRentalStatusType().name());
    rentalDto.setUserDto(userDtoMapper.mapEntityToDto(entity.getUser()));
    rentalDto.setLimitedTime(entity.getRentalDetails().getIndefiniteTime());

    return rentalDto;
  }
}
