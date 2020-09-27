package com.miwi.carrental.web;

import com.miwi.carrental.control.service.car.CarService;
import com.miwi.carrental.models.entity.Car;
import java.util.List;
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
    return ResponseEntity.ok().body(carService.findAll());
  }
}