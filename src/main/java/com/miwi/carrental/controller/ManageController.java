package com.miwi.carrental.controller;

import com.miwi.carrental.domain.dto.CarDto;
import com.miwi.carrental.domain.entity.Car;
import com.miwi.carrental.service.CarService;
import java.net.URI;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    if (checkErrors(bindingResult)) {
      return ResponseEntity.badRequest().build();
    }
    Car newCar = carService.createNewCar(car);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("cars/{id}")
        .buildAndExpand(newCar.getId())
        .toUri();

    return ResponseEntity.created(location).build();
  }

  @PatchMapping(path = "/edit-car/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Car> editCar(@Valid @RequestBody CarDto carDto, @PathVariable("id") Long id,
      BindingResult bindingResult) {
    if (checkErrors(bindingResult)) {
      return ResponseEntity.badRequest().build();
    }
    Car car = carService.editCar(id, carDto);
    return ResponseEntity.ok().body(car);
  }

  @DeleteMapping(path = "/delete/{id}")
  public ResponseEntity<Car> deleteCarById(@PathVariable("id") Long id) {
    if (carService.findById(id) != null) {
      carService.deleteById(id);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }/*

  @PatchMapping(path = "/make-availability/{carId}/{status}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Car> makeAvailability(@PathVariable("carId") Long carId,
      @PathVariable("status") String carStatus) {
    if (carService.findById(carId).isEmpty() || carStatus.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    Car car = carService.findById(carId).get();
    carService.changeToAvailable(carId, carStatus);
    return ResponseEntity.ok().body(car);
  }
*/

  private boolean checkErrors(BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      System.out.println("there were errors");
      bindingResult.getAllErrors().forEach(error -> {
        String errorObjectName = error.getObjectName();
        logger.warn("Validation failed - Object '{}': {}",
            errorObjectName.substring(0, 1).toUpperCase() + errorObjectName.substring(1),
            error.getDefaultMessage());
      });
      return true;
    }
    return false;
  }
}