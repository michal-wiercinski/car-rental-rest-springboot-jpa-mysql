package com.miwi.carrental.repository.view;

import com.miwi.carrental.domain.view.RentalView;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalViewDao extends JpaRepository<RentalView, Long> {

  List<RentalView> findByUserEmail(String email);

  List<RentalView> findByUserEmail(String email, Sort sort);

}
