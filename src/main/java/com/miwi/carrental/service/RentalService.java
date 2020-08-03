/*
package com.miwi.carrental.service;

import com.miwi.carrental.domain.dto.RentalDto;
import com.miwi.carrental.domain.entity.Rental;
import com.miwi.carrental.domain.entity.RentalDetails;
import com.miwi.carrental.exception.MyResourceNotFoundException;
import com.miwi.carrental.exception.RestPreconditions;
import com.miwi.carrental.mapper.dto.RentalDtoMapper;
import com.miwi.carrental.repository.RentalDao;
import com.miwi.carrental.security.service.UserService;
import com.miwi.carrental.service.generic.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RentalService extends GenericService<Rental> {

  private Logger logger = LoggerFactory.getLogger(getClass().getName());

  private final RentalDao rentalDao;
  private final CarService carService;
  private final RentalDetailService rentalDetailService;
  private final UserService userService;
  private final RentalDtoMapper rentalDtoMapper;

  public RentalService(final RentalDao rentalDao,
      final CarService carService,
      final RentalDetailService rentalDetailService,
      UserService userService, RentalDtoMapper rentalDtoMapper) {
    this.rentalDao = rentalDao;
    this.carService = carService;
    this.rentalDetailService = rentalDetailService;
    this.userService = userService;
    this.rentalDtoMapper = rentalDtoMapper;
  }

  public void createRental(Rental rental, Long carId, String email) {
    try {
      rental.setCar(carService.findById(carId));
      rental.setUser(userService.findByEmail(email));
      rental.setRentalDetails(rentalDetailService.save(new RentalDetails()));
      save(rental);
    } catch (UsernameNotFoundException u) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found", u);
    }
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
}*/
