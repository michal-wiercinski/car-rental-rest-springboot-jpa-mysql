package com.miwi.carrental.security.repository;

import com.miwi.carrental.domain.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailDao extends JpaRepository<UserDetail, Long> {

}
