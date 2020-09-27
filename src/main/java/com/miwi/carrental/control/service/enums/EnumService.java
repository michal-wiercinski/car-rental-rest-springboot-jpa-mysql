package com.miwi.carrental.control.service.enums;

import com.miwi.carrental.models.enums.BodyTypeName;
import com.miwi.carrental.models.enums.CarStatusType;
import com.miwi.carrental.models.enums.FuelType;
import com.miwi.carrental.models.enums.GearboxType;
import com.miwi.carrental.models.enums.RentalStatusType;
import com.miwi.carrental.models.enums.RoleName;
import com.miwi.carrental.models.enums.WheelDrive;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class EnumService {

  private final Map<String, Set<Enum<?>>> enumsMap = new HashMap<>();


  public Map<String, Set<Enum<?>>> getAllEnum() {
    enumsMap.put("Car status", Set.of(CarStatusType.values()));
    enumsMap.put("Body type", Set.of(BodyTypeName.values()));
    enumsMap.put("Fuel type", Set.of(FuelType.values()));
    enumsMap.put("Gearbox", Set.of(GearboxType.values()));
    enumsMap.put("Rental status", Set.of(RentalStatusType.values()));
    enumsMap.put("Role name", Set.of(RoleName.values()));
    enumsMap.put("Wheel drive", Set.of(WheelDrive.values()));
    enumsMap.put("Wheel drive type", Set.of(WheelDrive.values()));

    return enumsMap;
  }

  public Set<String> getAllEnumsNameInCamelCase() {
    if (enumsMap.isEmpty()) {
      getAllEnum();
    }
    enumsMap.keySet().forEach(System.out::println);
    Set<String> setOfEnumName = new HashSet<>();
    enumsMap.keySet().forEach((key) -> {
      String mappedName = "";
      if (!key.contains(" ")) {
        mappedName = key.toLowerCase();
      } else {
        while (key.contains(" ")) {
          int number = key.indexOf(" ");
          mappedName =
              key.substring(0, number).toLowerCase().trim()
                  + key.substring(number + 1, number + 2).toUpperCase() +
                  key.substring(number + 2);
        }
        System.out.println(mappedName);

        setOfEnumName.add(mappedName);
      }
    });

    return setOfEnumName;
  }
}