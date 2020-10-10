package com.miwi.carrental.control.mapper.dto;


import com.miwi.carrental.control.dto.UserDto;
import com.miwi.carrental.control.mapper.generic.GenericMapper;
import com.miwi.carrental.control.service.location.AddressService;
import com.miwi.carrental.models.entity.User;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper extends GenericMapper<User, UserDto> {

  private final AddressService addressService;

  public UserDtoMapper(final AddressService addressService) {
    this.addressService = addressService;
  }


  @Override
  public UserDto mapEntityToDto(User entity) {
    UserDto userDto = new UserDto();

    userDto.setId(entity.getId());
    userDto.setEmail(entity.getEmail());
    userDto.setFirstName(entity.getFirstName());
    userDto.setLastName(entity.getLastName());
    userDto.setAddressId(entity.getAddress().getId());
    userDto.setCity(entity.getAddress().getCity());
    userDto.setStreet(entity.getAddress().getStreet());
    userDto.setHouseNumber(entity.getAddress().getHouseNumber());
    userDto.setZipCode(entity.getAddress().getZipCode());

    return userDto;
  }

  @Override
  public User mapDtoToEntity(UserDto dto) {
    User user = new User();

    user.setId(dto.getId());
    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());
    user.setEmail(dto.getEmail());
    user.setPassword(dto.getPassword());
    user.setAddress(addressService.createAddressByUser(dto));

    return user;
  }

  public


  @Override
  public List<UserDto> mapEntityListToListDto(List<User> entityList) {
    return super.mapEntityListToListDto(entityList);
  }
}