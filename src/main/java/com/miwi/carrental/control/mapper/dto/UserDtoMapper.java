package com.miwi.carrental.control.mapper.dto;


import com.miwi.carrental.control.dto.UserDto;
import com.miwi.carrental.control.mapper.generic.GenericMapper;
import com.miwi.carrental.models.entity.User;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper extends GenericMapper<User, UserDto> {

  @Override
  public UserDto mapEntityToDto(User entity) {
    UserDto userDto = new UserDto();

    userDto.setId(entity.getId());
    userDto.setEmail(entity.getEmail());
    userDto.setFirstName(entity.getFirstName());
    userDto.setLastName(entity.getLastName());

    return userDto;
  }

  @Override
  public List<UserDto> mapEntityListToListDto(List<User> entityList) {
    return super.mapEntityListToListDto(entityList);
  }
}