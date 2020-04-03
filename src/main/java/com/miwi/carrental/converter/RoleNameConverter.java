package com.miwi.carrental.converter;

import com.miwi.carrental.domain.enums.RoleName;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RoleNameConverter implements AttributeConverter<RoleName, String> {

  @Override
  public String convertToDatabaseColumn(RoleName roleName) {
    if (roleName == null) {
      return null;
    }
    return roleName.getRoleName();
  }

  @Override
  public RoleName convertToEntityAttribute(String s) {
    if (s == null) {
      return null;
    }
    try {
      return RoleName.valueOf(s);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }
}
