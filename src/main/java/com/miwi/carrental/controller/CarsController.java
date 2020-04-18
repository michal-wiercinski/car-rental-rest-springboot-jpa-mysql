package com.miwi.carrental.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.miwi.carrental.domain.dto.CarDto;
import com.miwi.carrental.service.CarService;
import com.miwi.carrental.setter.SortSetter;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/cars", produces = MediaTypes.HAL_JSON_VALUE)
@CrossOrigin(origins = "*")
public class CarsController {

  private final CarService carService;
  private PagedResourcesAssembler<CarDto> pagedDtoAssembler;

  public CarsController(
      final CarService carService,
      PagedResourcesAssembler<CarDto> pagedDtoAssembler) {
    this.carService = carService;
    this.pagedDtoAssembler = pagedDtoAssembler;
  }

  /*@GetMapping
  public CollectionModel<EntityModel<CarDto>> getAll() {
    List<CarDto> carDtos = carService.getAllDtos();
    CollectionModel<EntityModel<CarDto>> carDtoCollectionModel = CollectionModel.wrap(carDtos);
    carDtoCollectionModel.add(linkTo(methodOn(CarsController.class).getAll()).withSelfRel());

    return carDtoCollectionModel;
  }*/

  @GetMapping
  public ResponseEntity<PagedModel<EntityModel<CarDto>>> getAllCars(Pageable pageable, Sort sort) {
    Page<CarDto> carsDto = carService
        .getAllDtos(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
    if (carsDto.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok().header("cache-control", "max-age" + "=120")
        .body(getPagedModelByDto(carsDto));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CarDto> getCarById(@PathVariable("id") Long id) {
    Optional<CarDto> optCarDto = carService.getCarDtoByCarId(id);
    if (optCarDto.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    CarDto carDto = optCarDto.get();
    carDto.add(
        linkTo(methodOn(CarsController.class).getAllCars(Pageable.unpaged(), Sort.unsorted()))
            .withRel("carsList"));

    return ResponseEntity.ok().body(carDto);
  }

  @GetMapping("/lookForAvailability/{status}")
  private ResponseEntity<PagedModel<EntityModel<CarDto>>> getAllAvailableCars(
      Pageable pageable, Sort sort,
      @PathVariable("status") Optional<String> availabilityParam) {
    Page<CarDto> carsDto = carService
        .findByAvailability(availabilityParam,
            PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
    if (carsDto.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok().body(getPagedModelByDto(carsDto));
  }

  private PagedModel<EntityModel<CarDto>> getPagedModelByDto(Page<CarDto> carsDto) {
    PagedModel<EntityModel<CarDto>> carDtoPagedModel = pagedDtoAssembler
        .toModel(carsDto);

    carDtoPagedModel.getContent()
        .forEach(carDtoEntityModel -> carDtoEntityModel.add(linkTo(methodOn(CarsController.class)
            .getCarById(carDtoEntityModel.getContent().getId())).withRel("car")));

    return carDtoPagedModel;
  }
}