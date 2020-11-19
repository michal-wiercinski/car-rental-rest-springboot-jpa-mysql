package com.miwi.carrental.control.repository;

import com.miwi.carrental.models.entity.CarParameter;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CarParameterDaoImpl {

  @PersistenceContext
  EntityManager entityManager;

  public Optional<CarParameter> findByRentalDetailsId(Long rentalDetailsID){
    return Optional.ofNullable(entityManager.createNamedQuery("CarParameter.findCarParametersByRentalDetailsId",
        CarParameter.class).setParameter("param", rentalDetailsID).getSingleResult());
  }
}
