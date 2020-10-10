package com.miwi.carrental.control.service.location;

import com.miwi.carrental.control.dto.LocationDto;
import com.miwi.carrental.control.dto.UserDto;
import com.miwi.carrental.control.repository.AddressDao;
import com.miwi.carrental.control.service.generic.GenericService;
import com.miwi.carrental.models.entity.Address;
import com.miwi.carrental.security.payload.request.RegistrationRequest;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends GenericService<Address> {

  private final AddressDao addressDao;

  public AddressService(final AddressDao addressDao) {
    this.addressDao = addressDao;
  }


  public Address getAddressFromCarLocation(LocationDto locationDto) {
    if (locationDto.getCity() != null && locationDto.getZipCode() != null) {
      Optional<Address> address = addressDao
          .findByCityAndStreetAndHouseNumber(locationDto.getCity(),
              locationDto.getStreet(), locationDto.getHouseNumber());

      return address.orElseGet(() -> save(new Address(
          locationDto.getCity(),
          locationDto.getStreet(),
          locationDto.getHouseNumber(),
          locationDto.getZipCode()
      )));
    }
    return new Address();
  }

  @Transactional
  public Address createAddressByUser(RegistrationRequest regRequest) {
    return getAddress(regRequest.getCity(), regRequest.getZipCode(), regRequest.getStreet(),
        regRequest.getHouseNumber());
  }

  @Transactional
  public Address createAddressByUser(UserDto userDto) {
    return getAddress(userDto.getCity(), userDto.getZipCode(), userDto.getStreet(),
        userDto.getHouseNumber());
  }


  @Transactional
  public Address editAddressByUser(UserDto userDto){
    System.out.println(userDto.getAddressId());
    Address address = addressDao.getOne(userDto.getAddressId());
      address.setCity(userDto.getCity());
 //   }
  //  if(userDto.getStreet() != null) {
      address.setStreet(userDto.getStreet());
 //   }
  //  if(userDto.getHouseNumber() != null){
      address.setHouseNumber(userDto.getHouseNumber());
  //  }
   // if(userDto.getZipCode() != null){
      address.setZipCode(userDto.getZipCode());
   // }
    return address;
  }

  private Address getAddress(String city, String zipCode, String street, String houseNumber) {
    Optional<Address> address;
    if (city != null && zipCode != null) {
      address = addressDao
          .findByCityAndStreetAndHouseNumber(city, street,
              houseNumber);
      return address.orElseGet(() -> save(new Address(
          city,
          street,
          houseNumber,
          zipCode
      )));
    }
    return new Address();
  }

  @Override
  public Address save(Address address) {
    return addressDao.save(address);
  }
}
