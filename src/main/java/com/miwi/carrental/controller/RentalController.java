package com.miwi.carrental.controller;


import com.miwi.carrental.assembler.RentalViewModelAssembler;
import com.miwi.carrental.domain.entity.Rental;
import com.miwi.carrental.domain.entity.RentalDetails;
import com.miwi.carrental.domain.view.RentalView;
import com.miwi.carrental.service.entityservice.RentalDetailService;
import com.miwi.carrental.service.entityservice.RentalService;
import com.miwi.carrental.service.viewservice.CarViewUserService;
import com.miwi.carrental.service.viewservice.RentalViewService;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rent-car", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class RentalController {

  private final CarViewUserService carViewUserService;
  private final RentalService rentalService;
  private final RentalViewService rentalViewService;
  private final RentalDetailService rentalDetailService;
  private final RentalViewModelAssembler rentalViewModelAssembler;

  public RentalController(
      final CarViewUserService carViewUserService,
      final RentalService rentalService,
      final RentalViewService rentalViewService,
      final RentalDetailService rentalDetailService,
      final RentalViewModelAssembler rentalViewModelAssembler) {
    this.carViewUserService = carViewUserService;
    this.rentalService = rentalService;
    this.rentalViewService = rentalViewService;
    this.rentalDetailService = rentalDetailService;
    this.rentalViewModelAssembler = rentalViewModelAssembler;
  }

  @GetMapping(value = {"/my-rent", "/my-rent/{sort},{direction}"})
  public ResponseEntity<List<RentalView>> getMyRent(
      @PathVariable(name = "sort", required = false) Optional<String> sortParam,
      @PathVariable(name = "direction", required = false) Optional<String> directionParam,
      HttpServletRequest httpServletRequest) {
    List<RentalView> rentals;
    Principal principal = httpServletRequest.getUserPrincipal();
    if (principal != null) {
      if (sortParam.isPresent() && directionParam.isPresent()) {
        rentals = rentalViewService
            .findAllByEmailAndSortByParam(httpServletRequest.getUserPrincipal().getName(),
                sortParam.get(), directionParam.get());
      } else {
        rentals = rentalViewService.findByEmail(principal.getName());
      }
      if (rentals.isEmpty()) {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(rentals, HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
  }

  @GetMapping(value = {"/all-rent", "/all-rent/{sort},{direction}"})
  public ResponseEntity<List<RentalView>> getAllRental(
      @PathVariable(name = "sort", required = false) Optional<String> sortParam,
      @PathVariable(name = "direction", required = false) Optional<String> directionParam) {
    List<RentalView> rentals;
    if (sortParam.isPresent() && directionParam.isPresent()) {
      rentals = rentalViewService
          .findAllAndSortByParam(sortParam.get(), directionParam.get());
    } else {
      rentals = rentalViewService.findAll();
    }
    if (rentals.isEmpty()) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(rentals, HttpStatus.OK);
  }

  @PostMapping(path = "create/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Rental> rentFormById(@PathVariable("id") Long carId,
      HttpServletRequest servletRequest) {
    if (carId == null || carId < 0) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    Principal principal = servletRequest.getUserPrincipal();
    Rental rental = rentalService.createRental(carId, principal.getName());

    return new ResponseEntity<>(rental, HttpStatus.CREATED);
  }

  @PatchMapping(path = "/cancel/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Rental> cancelRent(@PathVariable("id") Long rentalId) {
    if (rentalId == null || rentalId < 0) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    Rental rental = null;
    if (rentalService.findById(rentalId).isPresent()) {
      rental = rentalService.findById(rentalId).get();
      RentalDetails rentalDetails = rental.getRentalDetails();

      rentalDetailService.updateDate(rentalDetails.getId());
      rentalService.updateStatus(rentalId);
    }
    return new ResponseEntity<>(rental, HttpStatus.OK);
  }
}