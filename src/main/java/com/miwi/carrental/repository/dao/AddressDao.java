package com.miwi.carrental.repository.dao;

import com.miwi.carrental.domain.entity.Address;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDao extends JpaRepository<Address, Long> {

  Optional<Address> findByCityAndStreetAndHouseNumber(String city, String street,
      String houseNumber);

}
