package com.miwi.carrental.service.entityservice;

import com.miwi.carrental.domain.dto.UserDto;
import com.miwi.carrental.domain.entity.Address;
import com.miwi.carrental.repository.dao.AddressDao;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AddressService {

  private final AddressDao addressDao;

  public AddressService(final AddressDao addressDao) {
    this.addressDao = addressDao;
  }

  public Address save(Address address) {
    return addressDao.save(address);
  }

  @Transactional
  public Address createAddressByUserDto(UserDto userDto) {
    Address address = new Address();
    address.setCity(userDto.getCity());
    address.setStreet(userDto.getCity());
    address.setHouseNumber(userDto.getHouseNumber());
    address.setZipCode(userDto.getZipCode());
    save(address);
    return address;
  }
}
