package com.miwi.carrental.controller;

import com.miwi.carrental.domain.entity.Car;
import com.miwi.carrental.domain.view.CarViewUser;
import com.miwi.carrental.service.entityservice.CarService;
import com.miwi.carrental.service.viewservice.CarViewUserService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class IndexController {

  private final CarService carService;

  public IndexController(
      final CarService carService) {
    this.carService = carService;
  }

  @GetMapping("/")
  public ResponseEntity<List<Car>> getWelcomePage() {
    return new ResponseEntity<>(carService.findAll(), HttpStatus.OK);
  }
}