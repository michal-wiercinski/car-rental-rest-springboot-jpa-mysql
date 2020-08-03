/*
package com.miwi.carrental.controller;

import com.miwi.carrental.domain.dto.UserDto;
import com.miwi.carrental.domain.entity.User;
import com.miwi.carrental.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

  private Logger logger = LoggerFactory.getLogger(getClass().getName());
  private final UserService userService;

  public RegistrationController(final UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public String registerForm(Model model) {
    model.addAttribute("user", new UserDto());
    return "registration";
  }

  @PostMapping
  public String processRegistration(
      @Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      System.out.println("there were errors");
      bindingResult.getAllErrors().forEach(error -> {
        String errorObjectName = error.getObjectName();
        logger.warn("Validation failed - Object '{}': {}",
            errorObjectName.substring(0, 1).toUpperCase() + errorObjectName.substring(1),
            error.getDefaultMessage());
      });
      return "registration";
    }
    final User user = userService.registrationNewUser(userDto);
    if (user == null) {
      bindingResult.rejectValue("email", "message.regError", "This email address is already taken");
      return "registration";
    }
    return "redirect:/login";
  }
}*/
