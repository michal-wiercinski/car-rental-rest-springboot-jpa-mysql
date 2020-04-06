package com.miwi.carrental.repository.dao;

import com.miwi.carrental.domain.entity.Car;
import com.miwi.carrental.domain.entity.CarStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDao extends GenericDao<Car> {

  @Procedure(procedureName = "change_to_available_if_not_rented")
  void changeToAvailable(@Param("p_pk_car") Long carId,
      @Param("p_pk_car_status") String carStatusId);

  List<Car> findAllByCarStatusLike(Sort sort, CarStatus carStatus);
}
