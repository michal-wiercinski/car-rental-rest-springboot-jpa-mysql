package com.miwi.carrental.security.service;

import com.miwi.carrental.domain.dto.UserDto;
import com.miwi.carrental.domain.entity.Address;
import com.miwi.carrental.domain.entity.UserDetail;
import com.miwi.carrental.security.repository.UserDetailDao;
import com.miwi.carrental.service.entityservice.AddressService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService {

  private final UserDetailDao userDetailDao;
  private final AddressService addressService;

  public UserDetailService(final UserDetailDao userDetailDao,
      final AddressService addressService) {
    this.userDetailDao = userDetailDao;
    this.addressService = addressService;
  }

  public UserDetail save(UserDetail userDetail) {
    return userDetailDao.save(userDetail);
  }

  public UserDetail createByUserDetailDto(UserDto userDto) {
    UserDetail userDetail = new UserDetail();
    Address address = addressService.createAddressByUserDto(userDto);
    userDetail.setAddress(address);
    userDetailDao.save(userDetail);
    return userDetail;
  }
}
