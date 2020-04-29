package com.miwi.carrental.domain.entity;

import com.miwi.carrental.converter.RoleNameConverter;
import com.miwi.carrental.domain.enums.RoleName;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;


@Table(name = "role")
@Entity
public class Role implements Serializable {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "PK_role")
  private Long id;

  @Convert(converter = RoleNameConverter.class)
  @Column(name = "role_name")
  private RoleName name;

  @ManyToMany(mappedBy = "roles")
  private List<User> userList;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public RoleName getName() {
    return name;
  }

  public void setName(RoleName name) {
    this.name = name;
  }

  public List<User> getUserList() {
    return userList;
  }

  public void setUserList(List<User> userList) {
    this.userList = userList;
  }
}