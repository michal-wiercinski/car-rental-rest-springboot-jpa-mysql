package com.miwi.carrental.control.repository;

import com.miwi.carrental.domain.entity.CarModel;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class CarModelDaoImpl {

  @PersistenceContext
  private EntityManager entityManager;

  public List<CarModel> searchByCarModelNameOrBrandName(String param, int limit) {
    return entityManager.createNamedQuery("CarModel.FindByNameWithConcat", CarModel.class)
        .setParameter("param", param)
        .setMaxResults(limit).getResultList();
  }

}
