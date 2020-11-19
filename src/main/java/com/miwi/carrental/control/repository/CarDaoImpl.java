package com.miwi.carrental.control.repository;

import com.miwi.carrental.models.entity.Car;
import com.miwi.carrental.models.enums.ECarStatus;
import com.miwi.carrental.models.enums.ERentalStatus;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class CarDaoImpl {

  @PersistenceContext
  EntityManager entityManager;

  public Optional<Car> findRentedCarByRentalId(Long carId) {
    return Optional.ofNullable(
        entityManager.createNamedQuery("Car.findRentedCarByCarId", Car.class)
            .setParameter("carId", carId)
            .setParameter("rentalStatus", ERentalStatus.RENTED)
            .getSingleResult());
  }

}
