package com.miwi.carrental.security.payload.response;

import java.util.Set;

public class JwtResponse {

  private String token;
  private Long id;
  private String email;
  private Set<String> roles;

  private JwtResponse() {

  }

  public JwtResponse(String token, Long id, String email, Set<String> roles) {
    this.token = token;
    this.id = id;
    this.email = email;
    this.roles = roles;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getType() {
    return "Bearer";
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<String> getRoles() {
    return roles;
  }

  public void setRoles(Set<String> roles) {
    this.roles = roles;
  }
}
