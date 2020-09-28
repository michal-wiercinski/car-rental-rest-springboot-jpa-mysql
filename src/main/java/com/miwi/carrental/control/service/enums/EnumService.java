package com.miwi.carrental.control.service.enums;

import com.miwi.carrental.models.enums.EBodyType;
import com.miwi.carrental.models.enums.ECarStatus;
import com.miwi.carrental.models.enums.EFuelType;
import com.miwi.carrental.models.enums.EGearboxType;
import com.miwi.carrental.models.enums.ERentalStatus;
import com.miwi.carrental.models.enums.ERoleName;
import com.miwi.carrental.models.enums.EWheelDrive;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class EnumService {

  private final Map<String, Set<Enum<?>>> enumsMap = new HashMap<>();


  public Map<String, Set<Enum<?>>> getAllEnum() {
    enumsMap.put("Car status", Set.of(ECarStatus.values()));
    enumsMap.put("Body type", Set.of(EBodyType.values()));
    enumsMap.put("Fuel type", Set.of(EFuelType.values()));
    enumsMap.put("Gearbox", Set.of(EGearboxType.values()));
    enumsMap.put("Rental status", Set.of(ERentalStatus.values()));
    enumsMap.put("Role name", Set.of(ERoleName.values()));
    enumsMap.put("Wheel drive", Set.of(EWheelDrive.values()));
    enumsMap.put("Wheel drive type", Set.of(EWheelDrive.values()));

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