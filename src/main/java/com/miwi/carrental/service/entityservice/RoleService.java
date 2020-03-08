package com.miwi.carrental.service.entityservice;

import com.miwi.carrental.domain.entity.Role;
import com.miwi.carrental.repository.dao.RoleDao;
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
}
