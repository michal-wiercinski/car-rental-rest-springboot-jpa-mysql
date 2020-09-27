package com.miwi.carrental.control.repository;

import com.miwi.carrental.models.entity.Car;
import com.miwi.carrental.models.entity.CarStatus;
import com.miwi.carrental.models.enums.BodyTypeName;
import com.miwi.carrental.models.enums.FuelType;
import com.miwi.carrental.models.enums.GearboxType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDao extends GenericDao<Car>, QuerydslPredicateExecutor<Car> {

  @Procedure(procedureName = "change_to_available_if_not_rented")
  void changeToAvailable(@Param("p_pk_car") Long carId,
      @Param("p_pk_car_status") String carStatusId);

  Page<Car> findAllByCarStatusLike(CarStatus carStatus, Pageable pageable);

  Page<Car> findAllByCarParameter_BodyTypeTypeName(BodyTypeName bodyTypeName, Pageable pageable);

  Page<Car> findAllByCarParameter_DriveTrainGearboxType(GearboxType gearboxType, Pageable pageable);

  Page<Car> findAllByCarParameter_EngineFuelType(FuelType fuelType, Pageable pageable);
}
