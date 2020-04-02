package com.miwi.carrental.repository.dao;

import com.miwi.carrental.domain.entity.Role;
import com.miwi.carrental.domain.enums.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends GenericDao<Role> {

  //Optional<Role> findByName();

  @Query("SELECT r FROM Role r WHERE LOWER(r.name) = LOWER(:#{#role_name})")
  public Role findByRoleName(@Param("role_name") RoleName roleName);

}
