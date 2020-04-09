package com.miwi.carrental.controller;

import com.miwi.carrental.domain.dto.CarDto;
import com.miwi.carrental.service.CarService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/cars", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class CarsController {

  private final CarService carService;

  public CarsController(
      final CarService carService) {
    this.carService = carService;
  }

  @GetMapping(path = {"/our-fleet", "/our-fleet/{sort},{direction}"})
  public ResponseEntity<List<CarDto>> getAllCarsForAdmin(
      @PathVariable(name = "sort", required = false) Optional<String> sortParam,
      @PathVariable(name = "direction", required = false) Optional<String> directionParam) {
    List<CarDto> carDtos = carService
        .findAndSortAll(sortParam, directionParam);
    if (carDtos.isEmpty()) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(carDtos, HttpStatus.OK);
  }

  @GetMapping(path = {"/available", "available/sorting/{sort},{direction}"})
  private ResponseEntity<List<CarDto>> getAllAvailableCars(
      @PathVariable("sort") Optional<String> sortParam,
      @PathVariable("direction") Optional<String> directionParam,
      @PathVariable("availability") Optional<String> availabilityParam) {
    List<CarDto> availableCars = carService
        .findByAvailabilityAndSort(sortParam, directionParam, availabilityParam);
    if (availableCars.isEmpty()) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(availableCars, HttpStatus.OK);
  }
}