package com.miwi.carrental.controller;

import com.miwi.carrental.domain.view.CarViewAdmin;
import com.miwi.carrental.domain.view.CarViewUser;
import com.miwi.carrental.service.viewservice.CarViewAdminService;
import com.miwi.carrental.service.viewservice.CarViewUserService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/cars")
public class CarsController {

  private final CarViewAdminService carViewAdminService;
  private final CarViewUserService carViewUserService;

  public CarsController(
      final CarViewAdminService carViewAdminService,
      final CarViewUserService carViewUserService) {
    this.carViewAdminService = carViewAdminService;
    this.carViewUserService = carViewUserService;
  }

  @RequestMapping(value = {"/our-fleet",
      "/our-fleet/{sort},{direction}"}, method = RequestMethod.GET)
  public String getAllCars(
      @PathVariable(name = "sort", required = false) Optional<String> sortParam,
      @PathVariable(name = "direction", required = false) Optional<String> directionParam,
      Model model) {
    List<CarViewAdmin> detailsFleet;

    if (sortParam.isPresent() && directionParam.isPresent()) {
      detailsFleet = carViewAdminService
          .findAllAndSortByParam(sortParam.get(), directionParam.get());
    } else {
      detailsFleet = carViewAdminService.findAll();
    }

    model.addAttribute("detailsFleet", detailsFleet);
    return "fleetViewForAdmin";
  }

  @RequestMapping(value = {"/", "/{sort},{direction}"}, method = RequestMethod.GET)
  private String getAllCarsForUser(
      @PathVariable(name = "sort", required = false) Optional<String> sortParam,
      @PathVariable(name = "direction", required = false) Optional<String> directionParam,
      Model model) {
    List<CarViewUser> carsForUser;
    if (sortParam.isPresent() && directionParam.isPresent()) {
      carsForUser = carViewUserService
          .findAllAndSortByParam(sortParam.get(), directionParam.get());
    } else {
      carsForUser = carViewUserService.findAll();
    }

    model.addAttribute("carsForUser", carsForUser);
    return "fleetForUser";
  }

  @RequestMapping(path = {"/available",
      "available/sorting/{sort},{direction}"}, method = RequestMethod.GET)
  private String getAllAvailableCars(@PathVariable("sort") Optional<String> sortParam,
      @PathVariable("direction") Optional<String> directionParam, Model model) {
    List<CarViewUser> availableCars;

    if (sortParam.isPresent() && directionParam.isPresent()) {
      availableCars = carViewUserService
          .findAllAvailable(sortParam.get(), directionParam.get());
    } else {
      availableCars = carViewUserService.findAllAvailable();
    }

    model.addAttribute("carsForUser", availableCars                            );
    return "fleetForUser";
  }
}