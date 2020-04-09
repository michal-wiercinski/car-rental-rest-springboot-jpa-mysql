package com.miwi.carrental.service.entityservice;

import com.miwi.carrental.domain.dto.RentalDto;
import com.miwi.carrental.domain.entity.RentalDetails;
import com.miwi.carrental.repository.dao.RentalDetailsDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalDetailService implements IGenericService<RentalDetails> {

  private final RentalDetailsDao rentalDetailsDao;

  public RentalDetailService(
      final RentalDetailsDao rentalDetailsDao) {
    this.rentalDetailsDao = rentalDetailsDao;
  }

  public void updateDate(Long id) {
    rentalDetailsDao.updateEndDateById(id);
  }


  @Override
  public List<RentalDetails> findAll() {
    return null;
  }

  @Override
  public RentalDetails save(RentalDetails entity) {
    return rentalDetailsDao.save(entity);
  }

  @Override
  public Optional<RentalDetails> findById(Long id) {
    return Optional.empty();
  }

  @Override
  public void delete(RentalDetails entity) {

  }

  @Override
  public void deleteById(Long id) {

  }
}
