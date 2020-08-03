package com.miwi.carrental.controller;

import com.miwi.carrental.domain.dto.LocationDto;
import com.miwi.carrental.mapper.dto.LocationDtoMapper;
import com.miwi.carrental.service.LocationService;
import java.util.List;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/location", produces = MediaTypes.HAL_JSON_VALUE)
public class LocationController {

  private final LocationService locationService;
  private final LocationDtoMapper locationDtoMapper;

  public LocationController(LocationService locationService,
      LocationDtoMapper locationDtoMapper) {
    this.locationService = locationService;
    this.locationDtoMapper = locationDtoMapper;
  }

  @GetMapping("/search")
  public ResponseEntity<List<LocationDto>> getByNameOrStreetOrCityOrZipCode(
      @RequestParam("param") String param) {
    List<LocationDto> locationsDto = locationDtoMapper
        .mapEntityListToListDto(locationService.findByNameOrStreetOrCityOrZipCode(param));
    return ResponseEntity.ok().body(locationsDto);
  }


}
