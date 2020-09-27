package com.miwi.carrental.control.service.location;

import com.miwi.carrental.control.dto.LocationDto;
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
  public Address createAddressByUserDto(RegistrationRequest regRequest) {
    Optional<Address> address;
    if (regRequest.getCity() != null && regRequest.getZipCode() != null) {
      address = addressDao
          .findByCityAndStreetAndHouseNumber(regRequest.getCity(), regRequest.getStreet(),
              regRequest.getHouseNumber());
      return address.orElseGet(() -> save(new Address(
          regRequest.getCity(),
          regRequest.getStreet(),
          regRequest.getHouseNumber(),
          regRequest.getZipCode()
      )));
    }
    return new Address();
  }

  @Override
  public Address save(Address address) {
    return addressDao.save(address);
  }
}
