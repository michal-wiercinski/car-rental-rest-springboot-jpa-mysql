package com.miwi.carrental.security.service;

import com.miwi.carrental.domain.entity.User;
import com.miwi.carrental.security.MyUserPrincipal;
import com.miwi.carrental.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class MyUserDetailsService implements UserDetailsService {

  private Logger logger = LoggerFactory.getLogger(getClass().getName());

  private final RoleService roleService;
  private final UserService userService;

  public MyUserDetailsService(
      final RoleService roleService,
      final UserService userService) {
    this.roleService = roleService;
    this.userService = userService;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    Optional<User> user = userService.findByEmail(s);
    if (user.isPresent()) {
      logger.info("User {} has been found", user.get().getEmail());
      MyUserPrincipal myUserPrincipal = new MyUserPrincipal(user.get());
      logger.warn("Roles: {}", myUserPrincipal.getAuthorities());
      return new org.springframework.security.core.userdetails.User(
          myUserPrincipal.getUsername(),
          myUserPrincipal.getPassword(),
          myUserPrincipal.isEnabled(),
          myUserPrincipal.isAccountNonExpired(),
          myUserPrincipal.isCredentialsNonExpired(),
          myUserPrincipal.isAccountNonLocked(),
          myUserPrincipal.getAuthorities());
    } else {
      throw new UsernameNotFoundException(String.format("User: %s is not found", s));
    }
  }
}
