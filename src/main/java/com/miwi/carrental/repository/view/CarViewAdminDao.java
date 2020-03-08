package com.miwi.carrental.repository.view;

import com.miwi.carrental.domain.view.CarViewAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarViewAdminDao extends JpaRepository<CarViewAdmin, Long> {

}
