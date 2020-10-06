package com.miwi.carrental.web;

import com.miwi.carrental.control.dto.CarModelDto;
import com.miwi.carrental.control.mapper.dto.CarModelDtoMapper;
import com.miwi.carrental.control.service.car.CarModelService;
import java.util.List;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
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
