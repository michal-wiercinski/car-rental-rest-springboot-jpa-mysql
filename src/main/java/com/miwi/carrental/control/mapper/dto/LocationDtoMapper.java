package com.miwi.carrental.control.mapper.dto;

import com.miwi.carrental.control.dto.LocationDto;
import com.miwi.carrental.models.entity.Location;
import com.miwi.carrental.control.mapper.generic.GenericMapper;
import com.miwi.carrental.control.service.location.AddressService;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class LocationDtoMapper extends GenericMapper<Location, LocationDto> {

  private final AddressService addressService;

  public LocationDtoMapper(final AddressService addressService) {
    this.addressService = addressService;
  }

  @Transactional
  public Location mapDtoToEntity(LocationDto locationDto) {
    Location location = new Location();

    location.setName(locationDto.getName());
    location.setAddress(addressService.getAddressFromCarLocation(locationDto));
   /*
    location.setLatitude(locationDto.getLatitude());
    location.setLongitude(locationDto.getLongitude());
   */

    return location;
  }

  @Transactional
  public LocationDto mapEntityToDto(Location location) {
    LocationDto locationDto = new LocationDto();

    locationDto.setId(location.getId());
    locationDto.setName(location.getName());
/*
    locationDto.setAddressId(location.getAddress().getId());
    locationDto.setCity(location.getAddress().getCity());
    locationDto.setStreet(location.getAddress().getStreet());
    locationDto.setHouseNumber(location.getAddress().getHouseNumber());
    locationDto.setZipCode(location.getAddress().getZipCode());
*/
  /*
    locationDto.setLatitude(location.getLatitude());
    locationDto.setLongitude(location.getLongitude());
  */
    return locationDto;
  }
}