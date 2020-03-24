package com.miwi.carrental.controller;

import com.miwi.carrental.domain.view.CarViewAdmin;
import com.miwi.carrental.domain.view.CarViewUser;
import com.miwi.carrental.service.viewservice.CarViewAdminService;
import com.miwi.carrental.service.viewservice.CarViewUserService;
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

  private final CarViewAdminService carViewAdminService;
  private final CarViewUserService carViewUserService;

  public CarsController(
      final CarViewAdminService carViewAdminService,
      final CarViewUserService carViewUserService) {
    this.carViewAdminService = carViewAdminService;
    this.carViewUserService = carViewUserService;
  }

  @GetMapping(path = {"/our-fleet", "/our-fleet/{sort},{direction}"})
  public ResponseEntity<List<CarViewAdmin>> getAllCars(
      @PathVariable(name = "sort", required = false) Optional<String> sortParam,
      @PathVariable(name = "direction", required = false) Optional<String> directionParam) {
    List<CarViewAdmin> detailsFleet;
    if (sortParam.isPresent() && directionParam.isPresent()) {
      detailsFleet = carViewAdminService
          .findAllAndSortByParam(sortParam.get(), directionParam.get());
    } else {
      detailsFleet = carViewAdminService.findAll();
    }
    if (detailsFleet.isEmpty()) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(detailsFleet, HttpStatus.OK);
  }

  @GetMapping(path = {"/all", "/{sort},{direction}"})
  private ResponseEntity<List<CarViewUser>> getAllCarsForUser(
      @PathVariable(name = "sort", required = false) Optional<String> sortParam,
      @PathVariable(name = "direction", required = false) Optional<String> directionParam) {
    List<CarViewUser> carsForUser;
    if (sortParam.isPresent() && directionParam.isPresent()) {
      carsForUser = carViewUserService
          .findAllAndSortByParam(sortParam.get(), directionParam.get());
    } else {
      carsForUser = carViewUserService.findAll();
    }
   /* if (carsForUser.isEmpty()) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }*/
    return new ResponseEntity<>(carsForUser, HttpStatus.OK);
  }

  @GetMapping(path = {"/available", "available/sorting/{sort},{direction}"})
  private ResponseEntity<List<CarViewUser>> getAllAvailableCars(
      @PathVariable("sort") Optional<String> sortParam,
      @PathVariable("direction") Optional<String> directionParam) {
    List<CarViewUser> availableCars;
    if (sortParam.isPresent() && directionParam.isPresent()) {
      availableCars = carViewUserService
          .findAllAvailable(sortParam.get(), directionParam.get());
    } else {
      availableCars = carViewUserService.findAllAvailable();
    }
    if (availableCars.isEmpty()) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(availableCars, HttpStatus.OK);
  }
}