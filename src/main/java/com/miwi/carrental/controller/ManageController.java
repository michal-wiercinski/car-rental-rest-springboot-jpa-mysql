package com.miwi.carrental.controller;

import com.miwi.carrental.domain.dto.CarDto;
import com.miwi.carrental.domain.entity.BodyType;
import com.miwi.carrental.domain.entity.CarModel;
import com.miwi.carrental.domain.entity.CarStatus;
import com.miwi.carrental.domain.entity.Location;
import com.miwi.carrental.domain.view.CarViewAdmin;
import com.miwi.carrental.service.entityservice.BodyTypeService;
import com.miwi.carrental.service.entityservice.CarModelService;
import com.miwi.carrental.service.entityservice.CarService;
import com.miwi.carrental.service.entityservice.CarStatusService;
import com.miwi.carrental.service.entityservice.LocationService;
import com.miwi.carrental.service.viewservice.CarViewAdminService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/manage")
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

  @ModelAttribute
  public void getLists(final Model model) {
    List<BodyType> bodyTypes = bodyTypeService.findAll();
    List<CarModel> carModels = carModelService.findAll();
    List<Location> locations = locationService.findAll();
    List<CarStatus> carStatuses = carStatusService.findAll();

    model.addAttribute("bodyTypes", bodyTypes);
    model.addAttribute("carModels", carModels);
    model.addAttribute("locations", locations);
    model.addAttribute("carStatuses", carStatuses);
  }

  @GetMapping("/new-car")
  public String newCarForm(Model model) {
    CarDto newCar = new CarDto();
    model.addAttribute("car", newCar);
    return "carForm";
  }

  @PostMapping("/add-car")
  public String createCar(@Valid @ModelAttribute("car") CarDto car, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {

      System.out.println("there were errors");
      bindingResult.getAllErrors().forEach(error -> {
        String errorObjectName = error.getObjectName();
        logger.warn("Validation failed - Object '{}': {}",
            errorObjectName.substring(0, 1).toUpperCase() + errorObjectName.substring(1),
            error.getDefaultMessage());
      });
      return "carForm";
    }
    carService.createNewCar(car);
    return "index";
  }

  @GetMapping("/edit-form/{id}")
  public String editCarForm(Model model, @PathVariable("id") Optional<Long> idParam) {
    if (idParam.isPresent()) {
      Long idl = idParam.get();
      if (carViewAdminService.findById(idl).isPresent()) {
        CarViewAdmin car = carViewAdminService.findById(idl).get();
        model.addAttribute("editCar", car);
        model.addAttribute("carDto", new CarDto());
        return "editCarForm";
      }
    }
    return "redirect:/manage";
  }

  @PostMapping("/edit-car")
  public String editCar(@Valid @ModelAttribute CarDto carDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      System.out.println("there were errors");
      bindingResult.getAllErrors().forEach(error -> {
        String errorObjectName = error.getObjectName();
        logger.warn("Validation failed - Object '{}': {}",
            errorObjectName.substring(0, 1).toUpperCase() + errorObjectName.substring(1),
            error.getDefaultMessage());
      });
      return "editCarForm";
    }
    carService.createNewCar(carDto);
    return "index";
  }

  @RequestMapping(path = "/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
  public String deleteCarById(@PathVariable("id") Long id) {
    carService.deleteById(id);
    return "redirect:/cars/our-fleet";
  }

  @RequestMapping(path = "/make-availability/{carId}/{status}", method = {RequestMethod.POST,
      RequestMethod.GET})
  public String makeAvailability(@PathVariable("carId") Long carId,
      @PathVariable("status") String carStatus) {
    carService.changeToAvailable(carId, carStatus);
    return "redirect:/cars/our-fleet";
  }
}