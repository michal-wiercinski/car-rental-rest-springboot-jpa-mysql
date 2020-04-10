package com.miwi.carrental.controller;

import com.miwi.carrental.domain.dto.CarDto;
import com.miwi.carrental.service.CarService;
import com.miwi.carrental.setter.SortSetter;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping(path = {"/all", "/{sort},{direction}"}, params = {"page", "size"})
  public ResponseEntity<Page<CarDto>> getAllCarsForAdmin(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @PathVariable(name = "sort", required = false) Optional<String> sortParam,
      @PathVariable(name = "direction", required = false) Optional<String> directionParam) {
    Sort sort = SortSetter.setSort(sortParam, directionParam);
    Page<CarDto> carDtos = carService
        .getAllDtos(PageRequest.of(page, size, sort));
    System.out.println(carDtos.getTotalElements());
    if (carDtos.isEmpty()) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(carDtos, HttpStatus.OK);
  }

  @GetMapping(path = {"/available", "available/sorting/{sort},{direction}"}, params = {"page",
      "size"})
  private ResponseEntity<Page<CarDto>> getAllAvailableCars(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @PathVariable("sort") Optional<String> sortParam,
      @PathVariable("direction") Optional<String> directionParam,
      @PathVariable("availability") Optional<String> availabilityParam) {
    Sort sort = SortSetter.setSort(sortParam, directionParam);

    Page<CarDto> carDtos = carService
        .findByAvailability(availabilityParam, PageRequest.of(page, size, sort));
    if (carDtos.isEmpty()) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(carDtos, HttpStatus.OK);
  }
}