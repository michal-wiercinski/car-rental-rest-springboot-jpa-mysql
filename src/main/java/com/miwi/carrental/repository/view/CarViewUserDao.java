package com.miwi.carrental.repository.view;

import com.miwi.carrental.domain.view.CarViewUser;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarViewUserDao extends JpaRepository<CarViewUser, Long> {

  List<CarViewUser> findCarViewUserByCarStatusIsLike(String status);
  List<CarViewUser> findCarViewUserByCarStatusIsLike(String status, Sort sort);
}
