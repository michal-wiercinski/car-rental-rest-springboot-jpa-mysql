package com.miwi.carrental.security.mapper;

import com.miwi.carrental.domain.dto.UserDto;
import com.miwi.carrental.domain.entity.User;
import com.miwi.carrental.mapper.GenericMapper;
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
}
