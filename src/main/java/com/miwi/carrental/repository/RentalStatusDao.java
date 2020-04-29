package com.miwi.carrental.repository;

import com.miwi.carrental.domain.entity.RentalStatus;
import com.miwi.carrental.domain.enums.RentalStatusType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalStatusDao  extends JpaRepository<RentalStatus, Long> {

  Optional<RentalStatus> findByRentalStatusType(RentalStatusType rentalStatusType);
}
