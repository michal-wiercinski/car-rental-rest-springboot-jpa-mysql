package com.miwi.carrental.repository;

import com.miwi.carrental.domain.entity.CarModel;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface CarModelDao extends GenericDao<CarModel>{

  Optional<CarModel> findByName(String carModelName);

}
