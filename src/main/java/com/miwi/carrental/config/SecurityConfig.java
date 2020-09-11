package com.miwi.carrental.config;

import com.miwi.carrental.security.service.MyUserDetailsService;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  DataSource dataSource;

  @Autowired
  MyUserDetailsService userDetailsService;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/cars/make-availability/**")
        .access("hasAnyRole('ADMIN','SUPER_ADMIN')")
        .antMatchers(HttpMethod.POST, "/cars/**")
        .access("hasAnyRole('ADMIN','SUPER_ADMIN')")
        .antMatchers(HttpMethod.PUT, "/cars/**")
        .access("hasAnyRole('ADMIN','SUPER_ADMIN')")
        .antMatchers(HttpMethod.PATCH, "/cars/**")
        .access("hasRole('ADMIN')")
        .antMatchers(HttpMethod.DELETE, "/cars/**")
        .access("hasAnyRole('ADMIN','SUPER_ADMIN')")
        .antMatchers()
        .access("hasAnyRole('ADMIN','USER')")
        .antMatchers("/cars/**", "/registration")
        .access("permitAll")
        .and()
        .formLogin()
        .loginPage("/login")
        .usernameParameter("email")
        .passwordParameter("password")
        .permitAll()
        .and()
        .logout()
        .logoutSuccessUrl("/");

  }
}
