package com.miwi.carrental.security.userDetails;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.miwi.carrental.models.entity.User;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String email;

  @JsonIgnore
  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  public CustomUserDetails(Long id,  String email, String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserDetails build(User user) {
    return new CustomUserDetails(
        user.getId(),
        user.getEmail(),
        user.getPassword(),
        user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName().name()))
            .collect(Collectors.toList()));
  }


/*  private List<String> getPrivileges(Collection<Role> roles) {
    List<String> rolesName = new ArrayList<>();

    for (Role role : roles) {
      rolesName.add(role.getName());
    }
    return rolesName;
  }

  private Collection<GrantedAuthority> getGrantedAuthorities(List<String> rolesName) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (String roleName : rolesName) {
      authorities.add(new SimpleGrantedAuthority(roleName));
    }
    return authorities;
  }*/

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomUserDetails user = (CustomUserDetails) o;
    return Objects.equals(id, user.id);
  }
}
