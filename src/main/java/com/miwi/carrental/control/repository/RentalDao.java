package com.miwi.carrental.control.repository;

import com.miwi.carrental.models.entity.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalDao extends JpaRepository<Rental, Long> {

  Page<Rental> findAllByUser_Email(String email, Pageable pageable);
}
