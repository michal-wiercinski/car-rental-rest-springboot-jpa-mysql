package com.miwi.carrental.web;

import com.miwi.carrental.control.dto.UserDto;
import com.miwi.carrental.control.mapper.dto.UserDtoMapper;
import com.miwi.carrental.control.service.user.UserService;
import com.miwi.carrental.models.entity.User;
import com.miwi.carrental.security.jwt.JwtTokenService;
import com.miwi.carrental.security.payload.request.LoginRequest;
import com.miwi.carrental.security.payload.request.RegistrationRequest;
import com.miwi.carrental.security.payload.response.JwtResponse;
import com.miwi.carrental.security.userDetails.CustomUserDetails;
import com.miwi.carrental.web.utils.CheckerOfRequest;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


  public UserController(final UserService userService,
      final JwtTokenService jwtTokenService,
      final AuthenticationManager authenticationManager,
      UserDtoMapper userDtoMapper) {
    this.userService = userService;
    this.jwtTokenService = jwtTokenService;
    this.authenticationManager = authenticationManager;
    this.userDtoMapper = userDtoMapper;
  }

  @PostMapping(path = "/registration")
  public ResponseEntity<User> registration(
      @Valid @RequestBody RegistrationRequest request, BindingResult bindingResult) {
    if (CheckerOfRequest.checkErrors(bindingResult)) {
      return ResponseEntity.badRequest().build();
    }
    final User user = userService.registrationNewUser(request);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(user.getId())
        .toUri();

    return ResponseEntity.created(location).build();
  }

  @PostMapping(path = "/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request,
      BindingResult bindingResult) {
    if (CheckerOfRequest.checkErrors(bindingResult)) {
      return ResponseEntity.badRequest().build();
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
  public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
      @PathVariable("id") Long id, BindingResult bindingResult) {
    if (CheckerOfRequest.checkErrors(bindingResult)) {
      return ResponseEntity.badRequest().build();
    }
    userService.editUser(id, userDto);
    return ResponseEntity.ok().body(userDto);
  }
}