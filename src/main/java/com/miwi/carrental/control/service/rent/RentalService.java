package com.miwi.carrental.control.service.rent;

import com.miwi.carrental.control.dto.RentalDto;
import com.miwi.carrental.control.exception.MyResourceNotFoundException;
import com.miwi.carrental.control.mapper.dto.RentalDtoMapper;
import com.miwi.carrental.control.repository.RentalDao;
import com.miwi.carrental.control.service.user.UserService;
import com.miwi.carrental.control.service.car.CarService;
import com.miwi.carrental.control.service.generic.GenericService;
import com.miwi.carrental.models.entity.Rental;
import com.miwi.carrental.models.enums.ERentalStatus;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RentalService extends GenericService<Rental> {

  private Logger logger = LoggerFactory.getLogger(getClass().getName());

  private final RentalDao rentalDao;
  private final CarService carService;
  private final UserService userService;
  private final RentalDtoMapper rentalDtoMapper;
  private final RentalStatusService rentalStatusService;

  public RentalService(final RentalDao rentalDao,
      final CarService carService,
      final UserService userService,
      final RentalDtoMapper rentalDtoMapper,
      RentalStatusService rentalStatusService) {
    this.rentalDao = rentalDao;
    this.carService = carService;
    this.userService = userService;
    this.rentalDtoMapper = rentalDtoMapper;
    this.rentalStatusService = rentalStatusService;
  }

  @Transactional
  public Rental createRental(RentalDto rentalDto, Long carId, String email) {
    Rental rental = rentalDtoMapper.mapDtoToEntity(rentalDto);
    rental.setCar(carService.findById(carId));
    rental.setUser(userService.findByEmail(email));
    rental.setRentalStatus(rentalStatusService.findByStatus(ERentalStatus.RENTED));

    return save(rental);
  }

  @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
  public Page<Rental> getAll(Pageable pageable) {
    try {
      return rentalDao.findAll(pageable);
    } catch (Unauthorized ex) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access!!!");
    }
  }


  @PreAuthorize("isAuthenticated()")
  public Page<Rental> getAllByUserEmail(String email, Pageable pageable) {
    try {
      return checkFound(rentalDao.findAllByUser_Email(email, pageable));
    } catch (AccessDeniedException ade) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden message");
    } catch (MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The user with email: " + email + "don't have any rentals");
    }
  }

  public void updateStatus(Long id) {
    rentalDao.updateStatusById(id);
  }

  @Override
  public Rental save(Rental entity) {
    return rentalDao.save(entity);
  }

  @Override
  public Rental findById(Long id) {
    try {
      return checkFound(rentalDao.findById(id));
    } catch (MyResourceNotFoundException ex) {
      logger.warn("The rental with id: {} was not found ", id);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The rental with id: " + id + " was not found",
          ex);
    }
  }

  @Override
  public void delete(Rental entity) {
    rentalDao.delete(entity);
  }

  @Override
  public void deleteById(Long id) {
    delete(findById(id));
  }
}
