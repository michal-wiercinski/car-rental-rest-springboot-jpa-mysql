package com.miwi.carrental.security.service;

import com.miwi.carrental.domain.entity.User;
import com.miwi.carrental.security.MyUserPrincipal;
import javax.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

  private final UserService userService;

  public MyUserDetailsService(
      final UserService userService) {
    this.userService = userService;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    User user = userService.findByEmail(s);
    MyUserPrincipal myUserPrincipal = new MyUserPrincipal(user);
    return new org.springframework.security.core.userdetails.User(
        myUserPrincipal.getUsername(),
        myUserPrincipal.getPassword(),
        myUserPrincipal.isEnabled(),
        myUserPrincipal.isAccountNonExpired(),
        myUserPrincipal.isCredentialsNonExpired(),
        myUserPrincipal.isAccountNonLocked(),
        myUserPrincipal.getAuthorities());
  }
}
