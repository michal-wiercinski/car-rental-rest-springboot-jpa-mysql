package com.miwi.carrental.controller;

import com.miwi.carrental.domain.dto.CarDto;
import com.miwi.carrental.domain.entity.Car;
import com.miwi.carrental.service.CarService;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/manage", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ManageController {

  private Logger logger = LoggerFactory.getLogger(getClass().getName());

  private final CarService carService;

  public ManageController(
      final CarService carService) {
    this.carService = carService;
  }

  @PostMapping(path = "/add-car", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Car> createCar(@Valid @RequestBody CarDto car,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {

      System.out.println("there were errors");
      bindingResult.getAllErrors().forEach(error -> {
        String errorObjectName = error.getObjectName();
        logger.warn("Validation failed - Object '{}': {}",
            errorObjectName.substring(0, 1).toUpperCase() + errorObjectName.substring(1),
            error.getDefaultMessage());
      });
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    Car newCar = carService.createNewCar(car);

    return new ResponseEntity<>(newCar, HttpStatus.CREATED);
  }

  @PatchMapping(path = "/edit-car/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Car> editCar(@Valid @RequestBody CarDto carDto,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      System.out.println("there were errors");
      bindingResult.getAllErrors().forEach(error -> {
        String errorObjectName = error.getObjectName();
        logger.warn("Validation failed - Object '{}': {}",
            errorObjectName.substring(0, 1).toUpperCase() + errorObjectName.substring(1),
            error.getDefaultMessage());
      });
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    Car car = carService.editCar(carDto);
    return new ResponseEntity<>(car, HttpStatus.OK);
  }

  @DeleteMapping(path = "/delete/{id}")
  public ResponseEntity<Car> deleteCarById(@PathVariable("id") Long id) {
    if (!carService.findById(id).isPresent()) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    carService.deleteById(id);
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  @PatchMapping(path = "/make-availability/{carId}/{status}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Car> makeAvailability(@PathVariable("carId") Long carId,
      @PathVariable("status") String carStatus) {
    if (carService.findById(carId).isEmpty() || carStatus.isEmpty()) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    Car car = carService.findById(carId).get();
    carService.changeToAvailable(carId, carStatus);
    return new ResponseEntity<>(car, HttpStatus.OK);
  }
}