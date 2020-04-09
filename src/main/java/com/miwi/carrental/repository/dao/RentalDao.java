package com.miwi.carrental.repository.dao;

import com.miwi.carrental.domain.entity.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalDao extends JpaRepository<Rental, Long> {

  @Procedure(procedureName = "update_rental_status_by_pk")
  void updateStatusById(@Param("p_pk_rental") Long id);

  Page<Rental> findAllByUser_Email(String email, Pageable pageable);
}
