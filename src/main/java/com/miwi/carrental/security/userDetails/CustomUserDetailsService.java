package com.miwi.carrental.security.userDetails;

import com.miwi.carrental.control.service.user.UserService;
import com.miwi.carrental.models.entity.User;
import javax.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserService userService;

  public CustomUserDetailsService(
      final UserService userService) {
    this.userService = userService;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    User user = userService.findByEmail(s);

    return CustomUserDetails.build(user);
  }
}
