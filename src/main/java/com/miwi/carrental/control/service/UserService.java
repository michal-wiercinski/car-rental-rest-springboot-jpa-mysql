package com.miwi.carrental.control.service;


import com.miwi.carrental.control.exception.MyResourceNotFoundException;
import com.miwi.carrental.control.service.generic.GenericService;
import com.miwi.carrental.control.service.location.AddressService;
import com.miwi.carrental.models.entity.User;
import com.miwi.carrental.models.enums.RoleName;
import com.miwi.carrental.security.mapper.UserDtoMapper;
import com.miwi.carrental.security.payload.request.RegistrationRequest;
import com.miwi.carrental.control.repository.UserDao;
import com.miwi.carrental.security.validation.EmailExistsException;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService extends GenericService<User> {

  private final UserDao userDao;
  private final AddressService addressService;
  private final UserDtoMapper userDtoMapper;
  private final PasswordEncoder passwordEncoder;
  private final RoleService roleService;

  public UserService(final UserDao userDao,
      final AddressService addressService,
      final UserDtoMapper userDtoMapper,
      final PasswordEncoder passwordEncoder,
      final RoleService roleService) {
    this.userDao = userDao;
    this.addressService = addressService;
    this.userDtoMapper = userDtoMapper;
    this.passwordEncoder = passwordEncoder;
    this.roleService = roleService;
  }

  public User findByEmail(String email) {
    try {
      return checkFound(userDao.findByEmail(email));
    } catch (MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The user with email: " + email + " was not found",
          ex);
    }
  }


  public User registrationNewUser(RegistrationRequest reqRequest) {
    try {
      User user = createUser(reqRequest);
      return save(user);
    } catch (EmailExistsException e) {
      return null;
    }
  }

  private User createUser(RegistrationRequest regRequest)
      throws EmailExistsException {
    if (emailExist(regRequest.getEmail())) {
      throw new EmailExistsException("There is an account with that email address: "
          + regRequest.getEmail());
    }

    return User.builder()
        .firstName(regRequest.getFirstName())
        .lastName(regRequest.getLastName())
        .password(passwordEncoder.encode(regRequest.getPassword()))
        .email(regRequest.getEmail())
        .address(addressService.createAddressByUserDto(regRequest))
        .roles(Set.of(roleService.findByRoleName(RoleName.USER)))
        .build();
  }

  private boolean emailExist(String email) {
    return userDao.existsByEmail(email);
  }

  @Override
  public List<User> findAll() {
    return userDao.findAll();
  }

  @Override
  public User save(User entity) {
    return userDao.save(entity);
  }

  @Override
  public User findById(Long id) {
    try {
      return checkFound(userDao.findById(id));
    } catch (MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The user with id: " + id + " was not found",
          ex);
    }
  }

  @Override
  public void delete(User entity) {
  }

  @Override
  public void deleteById(Long id) {

  }

  @PostConstruct
  public void createSomeUser() {
    RegistrationRequest normalUserDto = new RegistrationRequest("Andrzej", "Andrzej", "andrzej@andrzej.pl", "proba",
        "proba", "Wrocław", "Warszawska", "34", "71-000");
    RegistrationRequest adminUserDto = new RegistrationRequest("Michał", "Michał", "michal@michal.pl", "proba",
        "proba", "Wrocław", "Warszawska", "33", "71-000");

    User user = registrationNewUser(adminUserDto);
    user.setRoles(Set.of(roleService.findByRoleName(RoleName.ADMIN),
        roleService.findByRoleName(RoleName.USER)));
    save(registrationNewUser(normalUserDto));
    save(user);
  }
}