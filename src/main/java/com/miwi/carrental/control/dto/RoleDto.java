package com.miwi.carrental.control.dto;

import org.springframework.hateoas.server.core.Relation;

@Relation(value = "role", collectionRelation = "roles")
public class RoleDto  {

  private Long id;
  private String name;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
