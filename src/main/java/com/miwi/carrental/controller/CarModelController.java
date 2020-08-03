package com.miwi.carrental.controller;

import com.miwi.carrental.domain.dto.CarModelDto;
import com.miwi.carrental.mapper.dto.CarModelDtoMapper;
import com.miwi.carrental.service.CarModelService;
import java.util.List;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/carModel", produces = MediaTypes.HAL_JSON_VALUE)
public class CarModelController {

  private final CarModelService carModelService;
  private final CarModelDtoMapper carModelDtoMapper;

  public CarModelController(CarModelService carModelService,
      CarModelDtoMapper carModelDtoMapper) {
    this.carModelService = carModelService;
    this.carModelDtoMapper = carModelDtoMapper;
  }

  @GetMapping("/search")
  public ResponseEntity<List<CarModelDto>> getByNameContain(@RequestParam("param") String param) {
    List<CarModelDto> carDto = carModelDtoMapper
        .mapEntityListToListDto(carModelService.findByParamContaining(param));
    return ResponseEntity.ok().body(carDto);
  }

}
