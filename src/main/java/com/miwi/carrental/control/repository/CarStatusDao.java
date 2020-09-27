package com.miwi.carrental.control.repository;

import com.miwi.carrental.models.entity.CarStatus;
import com.miwi.carrental.models.enums.CarStatusType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarStatusDao extends JpaRepository<CarStatus, String> {

  Optional<CarStatus> findByCarStatusName(CarStatusType carStatusType);
}
