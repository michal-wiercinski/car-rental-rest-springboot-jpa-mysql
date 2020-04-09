package com.miwi.carrental.controller;


import com.miwi.carrental.domain.dto.RentalDto;
import com.miwi.carrental.domain.entity.Rental;
import com.miwi.carrental.domain.entity.RentalDetails;
import com.miwi.carrental.service.RentalDetailService;
import com.miwi.carrental.service.RentalService;
import com.miwi.carrental.setter.SortSetter;
import java.security.Principal;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rent-car", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class RentalController {

  private final RentalService rentalService;
  private final RentalDetailService rentalDetailService;

  public RentalController(
      final RentalService rentalService,
      final RentalDetailService rentalDetailService) {
    this.rentalService = rentalService;
    this.rentalDetailService = rentalDetailService;
  }

  @GetMapping(value = {"/my-rent", "/my-rent/{sort},{direction}"})
  public ResponseEntity<Page<RentalDto>> getMyRent(
      @PathVariable(name = "sort", required = false) Optional<String> sortParam,
      @PathVariable(name = "direction", required = false) Optional<String> directionParam,
      @RequestParam(value = "page") int page,
      @RequestParam(value = "size") int size,
      HttpServletRequest httpServletRequest) {
    Page<RentalDto> rentals;
    Principal principal = httpServletRequest.getUserPrincipal();
    Sort sort = SortSetter.setSort(sortParam, directionParam);
    if (principal != null) {
      rentals = rentalService
          .getAllDtoByUserEmail(principal.getName(), PageRequest.of(page, size, sort));
      if (rentals.isEmpty()) {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(rentals, HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
  }


  @GetMapping(value = {"/all-rent", "/all-rent/{sort},{direction}"}, params = {"page", "size"})
  public ResponseEntity<Page<RentalDto>> getAllRental(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @PathVariable(name = "sort", required = false) Optional<String> sortParam,
      @PathVariable(name = "direction", required = false) Optional<String> directionParam) {
    Sort sort = SortSetter.setSort(sortParam, directionParam);
    Page<RentalDto> rentals = rentalService.getAllDtos(PageRequest.of(page, size, sort));

    if (rentals.isEmpty()) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(rentals, HttpStatus.OK);
  }

  @PostMapping(path = "create/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Rental> rentFormById(@RequestBody Rental rental,
      @PathVariable("id") Long carId,
      HttpServletRequest servletRequest) {

    if (carId == null || carId < 0) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    Principal principal = servletRequest.getUserPrincipal();
    rentalService.createRental(rental, carId, principal.getName());

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