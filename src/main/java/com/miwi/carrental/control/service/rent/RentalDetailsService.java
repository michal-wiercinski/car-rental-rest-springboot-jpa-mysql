package com.miwi.carrental.control.service.rent;

import com.miwi.carrental.control.dto.RentalDto;
import com.miwi.carrental.control.service.car.CarParameterService;
import com.miwi.carrental.models.entity.RentalDetails;
import com.miwi.carrental.control.repository.RentalDetailsDao;
import com.miwi.carrental.control.service.generic.GenericService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Service;

@Service
public class RentalDetailsService extends GenericService<RentalDetails> {

  private final RentalDetailsDao rentalDetailsDao;
  private final CarParameterService carParameterService;

  public RentalDetailsService(
      final RentalDetailsDao rentalDetailsDao,
      final CarParameterService carParameterService) {
    this.rentalDetailsDao = rentalDetailsDao;
    this.carParameterService = carParameterService;
  }

  public RentalDetails setValueAtTheCancelRental(RentalDetails rentalDetails) {
    rentalDetails.setEndDate(LocalDateTime.now());

    return rentalDetails;
  }

  public RentalDetails setValueAtTheEndOfRental(RentalDetails rentalDetails) {
    rentalDetails.setEndDate(LocalDateTime.now());
    rentalDetails.setDistance(
        calculateDistanceAfterRent(rentalDetails.getStartDate(), rentalDetails.getEndDate()));
    rentalDetails.setRentalCost(
        calculateRentalCost(rentalDetails.getStartDate(), rentalDetails.getEndDate(),
            rentalDetails.getDailyRate()));
    carParameterService.addDistanceToMileage(rentalDetails.getId(), rentalDetails.getDistance());

    return rentalDetails;
  }

  public RentalDetails createRentalDetails(RentalDto rentalDto) {
    RentalDetails rentalDetails = new RentalDetails();

    rentalDetails.setStartDate(rentalDto.getStartDate());
    rentalDetails.setEndDate(rentalDto.getEndDate());
    rentalDetails.setDailyRate(rentalDto.getDailyRate());
    rentalDetails.setIndefiniteTime(rentalDto.getLimitedTime());

    return rentalDetails;
  }

  @Override
  public RentalDetails save(RentalDetails entity) {
    return rentalDetailsDao.save(entity);
  }

  private int calculateDistanceAfterRent(LocalDateTime startDate, LocalDateTime endDate) {
    return (int) (ChronoUnit.SECONDS.between(startDate, endDate) * Math.random());
  }

  private BigDecimal calculateRentalCost(LocalDateTime startDate, LocalDateTime endDate,
      Double dailyRate) {
    return BigDecimal.valueOf((calculateDistanceAfterRent(startDate, endDate) * dailyRate) / 1000);
  }


}
