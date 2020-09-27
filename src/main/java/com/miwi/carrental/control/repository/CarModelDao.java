package com.miwi.carrental.control.repository;

import com.miwi.carrental.models.entity.CarModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarModelDao extends GenericDao<CarModel> {

  Optional<CarModel> findByName(String carModelName);

  @Query(value = "select cm from CarModel cm join Brand br on cm.brand.id = br.id where cm.name like concat('%', :param, '%') or br.name like concat('%', :param, '%')")
  default List<CarModel> searchInCarModelNameOrBrandName(@Param("param") String param) {
    return null;
  }

}
