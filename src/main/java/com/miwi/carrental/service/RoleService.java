package com.miwi.carrental.service;

import com.miwi.carrental.domain.entity.Role;
import com.miwi.carrental.domain.enums.RoleName;
import com.miwi.carrental.repository.RoleDao;
import com.miwi.carrental.service.generic.GenericService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends GenericService<Role> {

  private final RoleDao roleDao;

  public RoleService(final RoleDao roleDao) {
    this.roleDao = roleDao;
  }

  public Role findByRoleName(RoleName roleName) {

    return roleDao.findByName(roleName).orElseGet(this::getNewRoleWithUserType);
  }

  private Role getNewRoleWithUserType() {
    Role role = new Role();
    role.setName(RoleName.USER);
    return save(role);
  }

  @Override
  public Role save(Role role) {
    return roleDao.save(role);
  }

  @Override
  public Optional<Role> findById(Long id) {
    return roleDao.findById(id);
  }
}