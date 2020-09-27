package com.miwi.carrental.control.repository;

import com.miwi.carrental.models.entity.Location;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class LocationDaoImpl {

  @PersistenceContext
  EntityManager entityManager;

  public List<Location> searchByNameOrStreetOrCityOrZipCode(String param, int limit) {
    return entityManager.createNamedQuery("Location.findByNameWithConcat", Location.class)
        .setParameter("param", param)
        .setMaxResults(limit).getResultList();
  }
}
