package com.miwi.carrental.controller;

import com.miwi.carrental.domain.dto.CarDto;
import com.miwi.carrental.domain.entity.Car;
import com.miwi.carrental.service.entityservice.BodyTypeService;
import com.miwi.carrental.service.entityservice.CarModelService;
import com.miwi.carrental.service.entityservice.CarService;
import com.miwi.carrental.service.entityservice.CarStatusService;
import com.miwi.carrental.service.entityservice.LocationService;
import com.miwi.carrental.service.viewservice.CarViewAdminService;
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

  private final BodyTypeService bodyTypeService;
  private final CarModelService carModelService;
  private final LocationService locationService;
  private final CarService carService;
  private final CarStatusService carStatusService;
  private final CarViewAdminService carViewAdminService;

  public ManageController(
      final BodyTypeService bodyTypeService,
      final CarModelService carModelService,
      final LocationService locationService,
      final CarService carService,
      final CarStatusService carStatusService,
      final CarViewAdminService carViewAdminService) {
    this.bodyTypeService = bodyTypeService;
    this.carModelService = carModelService;
    this.locationService = locationService;
    this.carService = carService;
    this.carStatusService = carStatusService;
    this.carViewAdminService = carViewAdminService;
  }

  /*@ModelAttribute
  public void getLists(final Model model) {
    List<BodyType> bodyTypes = bodyTypeService.findAll();
    List<CarModel> carModels = carModelService.findAll();
    List<Location> locations = locationService.findAll();
    List<CarStatus> carStatuses = carStatusService.findAll();

    model.addAttribute("bodyTypes", bodyTypes);
    model.addAttribute("carModels", carModels);
    model.addAttribute("locations", locations);
    model.addAttribute("carStatuses", carStatuses);
  }*/

  /*@GetMapping("/new-car")
  public String newCarForm(Model model) {
    CarDto newCar = new CarDto();
    model.addAttribute("car", newCar);
    return "carForm";
  }*/

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
    Car car = carService.createNewCar(carDto);
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
    if (!carService.findById(carId).isPresent() || carStatus.isEmpty()) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    Car car = carService.findById(carId).get();
    carService.changeToAvailable(carId, carStatus);
    return new ResponseEntity<>(car, HttpStatus.OK);
  }
}