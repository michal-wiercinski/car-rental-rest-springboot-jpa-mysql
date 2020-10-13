package com.miwi.carrental.web;

import com.miwi.carrental.control.dto.UserDto;
import com.miwi.carrental.control.dto.password.PasswordChangeDto;
import com.miwi.carrental.control.dto.password.PasswordResetDto;
import com.miwi.carrental.control.mapper.dto.UserDtoMapper;
import com.miwi.carrental.control.service.EmailService;
import com.miwi.carrental.control.service.user.PasswordResetTokenService;
import com.miwi.carrental.control.service.user.UserService;
import com.miwi.carrental.models.entity.User;
import com.miwi.carrental.security.jwt.JwtTokenService;
import com.miwi.carrental.security.payload.request.LoginRequest;
import com.miwi.carrental.security.payload.request.RegistrationRequest;
import com.miwi.carrental.security.payload.response.JwtResponse;
import com.miwi.carrental.security.payload.response.MessageResponse;
import com.miwi.carrental.security.userDetails.CustomUserDetails;
import com.miwi.carrental.web.utils.CheckerOfRequest;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(path = "/users")
public class UserController {

  private Logger logger = LoggerFactory.getLogger(getClass().getName());
  private final UserService userService;
  private final JwtTokenService jwtTokenService;
  private final AuthenticationManager authenticationManager;
  private final UserDtoMapper userDtoMapper;
  private final EmailService emailService;
  private final MessageSource messageSource;
  private final PasswordResetTokenService passwordResetTokenService;


  public UserController(final UserService userService,
      final JwtTokenService jwtTokenService,
      final AuthenticationManager authenticationManager,
      final UserDtoMapper userDtoMapper,
      final EmailService emailService, MessageSource messageSource,
      PasswordResetTokenService passwordResetTokenService) {
    this.userService = userService;
    this.jwtTokenService = jwtTokenService;
    this.authenticationManager = authenticationManager;
    this.userDtoMapper = userDtoMapper;
    this.emailService = emailService;
    this.messageSource = messageSource;
    this.passwordResetTokenService = passwordResetTokenService;
  }

  @PostMapping(path = "/registration")
  public ResponseEntity<?> registration(
      @Valid @RequestBody RegistrationRequest request, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {

      return ResponseEntity.badRequest().body(CheckerOfRequest.checkErrors(bindingResult));
    }
    final User user = userService.registrationNewUser(request);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(user.getId())
        .toUri();

    return ResponseEntity.created(location)
        .body(new MessageResponse(String.format("User: %s has been created", user.getEmail())));
  }

  @PostMapping(path = "/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body(CheckerOfRequest.checkErrors(bindingResult));
    }

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
    String jwt = jwtTokenService.generateJwtToken(principal);
    Set<String> roles = principal.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toSet());

    return ResponseEntity
        .ok(new JwtResponse(jwt, principal.getId(), principal.getUsername(), roles));
  }

  @GetMapping(path = "/all")
  public ResponseEntity<List<UserDto>> getAll() {
    return ResponseEntity.ok().body(userDtoMapper.mapEntityListToListDto(userService.findAll()));
  }

  @GetMapping(path = "/get-user")
  public ResponseEntity<UserDto> getPrincipalUser(HttpServletRequest httpServletRequest) {
    String email = httpServletRequest.getUserPrincipal().getName();

    UserDto userDto = userDtoMapper.mapEntityToDto(userService.findByEmail(email));

    return ResponseEntity.ok().body(userDto);
  }

  @PutMapping(path = "/{id}")
  public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto,
      @PathVariable("id") Long id, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body(CheckerOfRequest.checkErrors(bindingResult));
    }
    userService.editUser(id, userDto);
    return ResponseEntity.ok()
        .body(new MessageResponse(String.format("User: %s has been created", userDto.getEmail())));
  }

  @PatchMapping(path = "/update-password")
  public ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordChangeDto passwordChangerDto,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body(CheckerOfRequest.checkErrors(bindingResult));
    }
    User user = userService
        .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

    if (!userService.checkIfValidOldPassword(user, passwordChangerDto.getOldPassword())) {
      return ResponseEntity.badRequest()
          .body(new MessageResponse("The old password entered differs from the real one"));
    }

    userService.changeUserPassword(user, passwordChangerDto.getNewPassword());
    return ResponseEntity.ok().body(new MessageResponse(("Password has been changed")));
  }

  @PostMapping(path = "/reset-password")
  public ResponseEntity<?> resetPassword(@RequestBody String email) {
    User user = userService.findByEmail(email);

    String token = UUID.randomUUID().toString();
    passwordResetTokenService.createPasswordResetTokenForUser(user, token);
    sendResetTokenEmail(token, user);

    return ResponseEntity.ok()
        .body(new MessageResponse("You should receive an Password Reset Email shortly"));
  }

  @PatchMapping("/change-password/{token}")
  public ResponseEntity<?> changePassword(@PathVariable("token") final String token,
      @Valid @RequestBody PasswordResetDto passwordResetDto, BindingResult bindingResult) {
    final String result = passwordResetTokenService
        .validatePasswordResetToken(token, passwordResetDto.getToken());

    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body(CheckerOfRequest.checkErrors(bindingResult));
    }

    final Optional<User> user = userService.getUserByPasswordResetToken(token);
    if (result == null && user.isPresent()) {
      userService.changeUserPassword(user.get(), passwordResetDto.getNewPassword());
      return ResponseEntity.ok()
          .body(new MessageResponse("Password changed for the user: " + user.get().getEmail()));
    } else {
      return ResponseEntity.badRequest()
          .body(new MessageResponse("Password change failed: " + result));
    }
  }

  private void sendResetTokenEmail(final String token, final User user) {
    final String url = "http://localhost:4200/change-password/" + token;
    final String body = "Reset your password\r\n" + url;

    emailService.sendMail(user.getEmail(), "Reset password", body);
  }
}