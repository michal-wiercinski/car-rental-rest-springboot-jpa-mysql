package com.miwi.carrental.web;

import com.miwi.carrental.control.service.enums.EnumService;
import java.util.Map;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/enums", produces = "application/json")
public class EnumController {

    private final EnumService enumService;

  public EnumController(EnumService enumService) {
    this.enumService = enumService;
  }

  @GetMapping
  public ResponseEntity<Map<String, Set<Enum<?>>>> readAllEnum() {
    Map<String, Set<Enum<?>>> enumsMap = enumService.getAllEnum();

    return ResponseEntity.ok().body(enumsMap);
  }

  @GetMapping(path = "/convertNames")
  public ResponseEntity<Set<String>> readAllConvertEnumNames() {
    Set<String> setOfEnumName = enumService.getAllEnumsNameInCamelCase();

    return ResponseEntity.ok().body(setOfEnumName);
  }
}