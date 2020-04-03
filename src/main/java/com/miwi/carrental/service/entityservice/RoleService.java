package com.miwi.carrental.service.entityservice;

import com.miwi.carrental.domain.entity.Role;
import com.miwi.carrental.domain.enums.RoleName;
import com.miwi.carrental.repository.dao.RoleDao;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

  private final RoleDao roleDao;

  public RoleService(final RoleDao roleDao) {
    this.roleDao = roleDao;
  }

  public Role findById(Long id) {
    return roleDao.findById(id).get();
  }

  public Role findByRoleName(RoleName roleName) {

    return roleDao.findByName(roleName).orElseGet(this::getNewRoleWithUserType);
  }

  public Role save(Role role) {
    return roleDao.save(role);
  }

  private Role getNewRoleWithUserType() {
    Role role = new Role();
    role.setName(RoleName.USER);
    return save(role);
  }
}