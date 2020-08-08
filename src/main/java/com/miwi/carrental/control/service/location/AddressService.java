package com.miwi.carrental.control.service.location;

import com.miwi.carrental.control.dto.LocationDto;
import com.miwi.carrental.domain.entity.Address;
import com.miwi.carrental.control.repository.AddressDao;
import com.miwi.carrental.control.service.generic.GenericService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends GenericService<Address> {

  private final AddressDao addressDao;

  public AddressService(final AddressDao addressDao) {
    this.addressDao = addressDao;
  }

  public Optional<Address> findByCityStreetHouseNumber(String city, String street,
      String houseNumber) {
    return addressDao.findByCityAndStreetAndHouseNumber(city, street, houseNumber);
  }

  public Address getAddressFromCarParam(LocationDto locationDto) {
    Optional<Address> address = findByCityStreetHouseNumber(locationDto.getCity(),
        locationDto.getStreet(), locationDto.getHouseNumber());

    if (address.isPresent()) {
      return address.get();
    }
    Address newAddress = new Address();
    newAddress.setCity(locationDto.getCity());
    newAddress.setStreet(locationDto.getStreet());
    newAddress.setHouseNumber(locationDto.getHouseNumber());
    newAddress.setZipCode(locationDto.getZipCode());

    return newAddress;
  }

/*  @Transactional
  public Address createAddressByUserDto(UserDto userDto) {
    Optional<Address> address = addressDao
        .findByCityAndStreetAndHouseNumber(userDto.getCity(), userDto.getStreet(),
            userDto.getHouseNumber());

    if (address.isPresent()) {
      return address.get();
    }
    Address newAddress = new Address();
    newAddress.setCity(userDto.getCity());
    newAddress.setStreet(userDto.getCity());
    newAddress.setHouseNumber(userDto.getHouseNumber());
    newAddress.setZipCode(userDto.getZipCode());
    save(newAddress);

    return newAddress;
  }*/

  @Override
  public Address save(Address address) {
    return addressDao.save(address);
  }
}
