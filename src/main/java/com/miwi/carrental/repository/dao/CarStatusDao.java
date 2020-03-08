package com.miwi.carrental.repository.dao;

import com.miwi.carrental.domain.entity.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarStatusDao extends JpaRepository<CarStatus, String> {


}
