package com.miwi.carrental.control.repository;

import com.miwi.carrental.models.entity.Role;
import com.miwi.carrental.models.enums.ERoleName;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends GenericDao<Role> {

  Optional<Role> findByName(ERoleName roleName);

/*  @Query("SELECT r FROM Role r WHERE LOWER(r.name) = LOWER(:#{#role_name})")
  public Role findByRoleName(@Param("role_name") RoleName roleName);*/
}
