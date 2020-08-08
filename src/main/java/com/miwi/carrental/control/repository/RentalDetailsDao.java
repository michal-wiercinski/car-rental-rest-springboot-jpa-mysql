package com.miwi.carrental.control.repository;

import com.miwi.carrental.domain.entity.RentalDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalDetailsDao extends JpaRepository<RentalDetails, Long> {

  @Procedure(procedureName = "update_end_date_by_pk")
  void updateEndDateById(@Param("p_pk_rental_details") Long id);
}
