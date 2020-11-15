package com.miwi.carrental.control.service.rent;

import com.miwi.carrental.control.dto.RentalDto;
import com.miwi.carrental.models.entity.Rental;
import com.miwi.carrental.models.entity.RentalDetails;
import com.miwi.carrental.control.repository.RentalDetailsDao;
import com.miwi.carrental.control.service.generic.GenericService;
import org.springframework.stereotype.Service;

@Service
public class RentalDetailsService extends GenericService<RentalDetails> {

  private final RentalDetailsDao rentalDetailsDao;

  public RentalDetailsService(
      final RentalDetailsDao rentalDetailsDao) {
    this.rentalDetailsDao = rentalDetailsDao;
  }

/*  public void updateDate(Long id) {
    rentalDetailsDao.updateEndDateById(id);
  }*/

  public RentalDetails createRentalDetails(RentalDto rentalDto){
    RentalDetails rentalDetails = new RentalDetails();

    rentalDetails.setStartDate(rentalDto.getStartDate());
    rentalDetails.setEndDate(rentalDto.getEndDate());
    rentalDetails.setIndefiniteTime(rentalDto.getLimitedTime());

    return save(rentalDetails);
  }

  @Override
  public RentalDetails save(RentalDetails entity) {
    return rentalDetailsDao.save(entity);
  }

}
