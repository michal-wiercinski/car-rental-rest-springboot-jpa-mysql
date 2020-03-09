package com.miwi.carrental.service.entityservice;

import com.miwi.carrental.domain.entity.Rental;
import com.miwi.carrental.domain.entity.RentalDetails;
import com.miwi.carrental.repository.dao.RentalDao;
import com.miwi.carrental.security.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService implements IGenericService<Rental> {

  private final RentalDao rentalDao;
  private final CarService carService;
  private final RentalDetailService rentalDetailService;
  private final UserService userService;

  public RentalService(final RentalDao rentalDao,
      final CarService carService,
      final RentalDetailService rentalDetailService,
      UserService userService) {
    this.rentalDao = rentalDao;
    this.carService = carService;
    this.rentalDetailService = rentalDetailService;
    this.userService = userService;
  }

  public void updateStatus(Long id) {
    rentalDao.updateStatusById(id);
  }

  @Override
  public List<Rental> findAll() {
    return null;
  }

  @Override
  public Rental save(Rental entity) {
    return rentalDao.save(entity);
  }

  @Override
  public Optional<Rental> findById(Long id) {
    return rentalDao.findById(id);
  }

  @Override
  public void delete(Rental entity) {

  }

  @Override
  public void deleteById(Long id) {

  }

  public Rental createRental(Long carId, String email) {
    Rental rental = new Rental();
    rental.setCar(carService.findById(carId).get());
    rental.setUser(userService.findByEmail(email).get());
    rental.setRentalDetails(rentalDetailService.save(new RentalDetails()));
    return save(rental);
  }
}