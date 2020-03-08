package com.miwi.carrental.controller;

import com.miwi.carrental.domain.view.CarViewUser;
import com.miwi.carrental.service.viewservice.CarViewUserService;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

  private final CarViewUserService carViewUserService;

  public IndexController(
      final CarViewUserService carViewUserService) {
    this.carViewUserService = carViewUserService;
  }

  @GetMapping("/")
  public List<CarViewUser> getWelcomePageWithStatus(Model model) {
    return carViewUserService.findAllAvailable();
  }
}