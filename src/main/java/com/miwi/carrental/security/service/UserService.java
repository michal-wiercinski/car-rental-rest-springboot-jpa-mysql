package com.miwi.carrental.security.service;

import com.miwi.carrental.domain.dto.UserDto;
import com.miwi.carrental.domain.entity.User;
import com.miwi.carrental.security.repository.UserDao;
import com.miwi.carrental.security.validation.EmailExistsException;
import com.miwi.carrental.service.entityservice.IGenericService;
import com.miwi.carrental.service.entityservice.RoleService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IGenericService<User> {

  private final UserDao userDao;
  private final UserDetailService userDetailService;
  private final PasswordEncoder passwordEncoder;
  private final RoleService roleService;

  public UserService(final UserDao userDao,
      final UserDetailService userDetailService,
      final PasswordEncoder passwordEncoder,
      final RoleService roleService) {
    this.userDao = userDao;
    this.userDetailService = userDetailService;
    this.passwordEncoder = passwordEncoder;
    this.roleService = roleService;
  }

  public Optional<User> findByEmail(String email) {
    return userDao.findByEmail(email);
  }

  @Transactional
  public User registrationNewUser(UserDto userDto) {
    User user = null;
    try {
      user = createByDto(userDto);
      user.setUserDetail(userDetailService.createByUserDetailDto(userDto));
      return save(user);
    } catch (EmailExistsException e) {
      return null;
    }
  }

  private User createByDto(UserDto userDto)
      throws EmailExistsException {
    if (emailExist(userDto.getEmail())) {
      throw new EmailExistsException("There is an account with that email address: "
          + userDto.getEmail());
    }
    User user = new User();
    user.setFirstName(userDto.getFirstName());
    user.setLastName(userDto.getLastName());
    user.setEmail(userDto.getEmail());
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    user.setRoles(Arrays.asList(roleService.findById(3L)));
    return user;
  }

  private boolean emailExist(String email) {
    return findByEmail(email).isPresent();
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
  public Optional<User> findById(Long id) {
    return userDao.findById(id);
  }

  @Override
  public void delete(User entity) {

  }

  @Override
  public void deleteById(Long id) {

  }

  @PostConstruct
  public void createSomeUser() {
    UserDto normalUserDto = new UserDto("Andrzej", "Andrzej", "andrzej@andrzej.pl", "proba",
        "proba", "Wrocław", "Warszawska", "34", "71-000");
    UserDto adminUserDto = new UserDto("Michał", "Michał", "michal@michal.pl", "proba",
        "proba", "Wrocław", "Warszawska", "33", "71-000");

    User user = registrationNewUser(adminUserDto);
    user.setRoles(Arrays.asList(roleService.findById(2L), roleService.findById(3L)));

    save(registrationNewUser(normalUserDto));
    save(user);
  }
}