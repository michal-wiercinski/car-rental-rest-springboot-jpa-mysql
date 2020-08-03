package com.miwi.carrental.controller;

import com.miwi.carrental.domain.entity.BodyType;
import com.miwi.carrental.service.BodyTypeService;
import java.util.List;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/bodyType", produces = MediaTypes.HAL_JSON_VALUE)
public class BodyTypeController {

  private final BodyTypeService bodyTypeService;

  public BodyTypeController(BodyTypeService bodyTypeService) {
    this.bodyTypeService = bodyTypeService;
  }

  @GetMapping("/search")
  public ResponseEntity<List<BodyType>> getByNameContain(@RequestParam("param") String param) {
    return ResponseEntity.ok().body(bodyTypeService.findByParamContaining(param));
  }
}
