package com.miwi.carrental.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.miwi.carrental.domain.dto.CarDto;
import com.miwi.carrental.domain.entity.Car;
import com.miwi.carrental.mapper.dto.CarDtoMapper;
import com.miwi.carrental.service.CarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/cars", produces = MediaTypes.HAL_JSON_VALUE)
public class CarsController {

  private final CarService carService;
  private final CarDtoMapper carDtoMapper;

  private PagedResourcesAssembler<CarDto> pagedDtoAssembler;

  public CarsController(
      final CarService carService,
      CarDtoMapper carDtoMapper,
      PagedResourcesAssembler<CarDto> pagedDtoAssembler) {
    this.carService = carService;
    this.carDtoMapper = carDtoMapper;
    this.pagedDtoAssembler = pagedDtoAssembler;
  }

  @GetMapping
  public ResponseEntity<PagedModel<EntityModel<CarDto>>> getAllCars(Pageable pageable, Sort sort) {
    Page<Car> carPage = carService
        .findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
    if (carPage == null) {
      return ResponseEntity.notFound().build();
    }
    Page<CarDto> carsDto = carDtoMapper.mapEntityPageToPageDto(carPage);

    PagedModel<EntityModel<CarDto>> carsPagedModel = getPagedModelByDto(carsDto);

    return ResponseEntity.ok().header("cache-control", "max-age" + "=120")
        .body(carsPagedModel);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CarDto> getCarById(@PathVariable("id") Long id) {
    Car car = carService.findById(id);
    CarDto carDto = carDtoMapper.mapEntityToDto(car);
    carDto.add(
        linkTo(methodOn(CarsController.class)
            .getAllCars(null, null))
            .withRel("carsList"));

    return ResponseEntity.ok().body(carDto);
  }

  @GetMapping(value = "/availability", params = "status")
  private ResponseEntity<PagedModel<EntityModel<CarDto>>> getByAvailability(
      Pageable pageable, Sort sort,
      @RequestParam(value = "status") String availabilityParam) {

    Page<Car> cars = carService
        .findByAvailability(availabilityParam,
            PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
    PagedModel<EntityModel<CarDto>> carsDto = getPagedModelByDto(
        carDtoMapper.mapEntityPageToPageDto(cars));

    return ResponseEntity.ok().body(carsDto);
  }


  private PagedModel<EntityModel<CarDto>> getPagedModelByDto(Page<CarDto> carsDto) {
    PagedModel<EntityModel<CarDto>> carDtoPagedModel = pagedDtoAssembler
        .toModel(carsDto);

    carDtoPagedModel.getContent()
        .forEach(carDtoEntityModel -> carDtoEntityModel.add(linkTo(methodOn(CarsController.class)
            .getCarById(carDtoEntityModel.getContent().getId())).withRel("car")));
    carDtoPagedModel.add(
        linkTo(methodOn(CarsController.class)
            .getAllCars(null, null))
            .withRel("carsList"));

    return carDtoPagedModel;
  }
   /*@GetMapping
  public CollectionModel<EntityModel<CarDto>> getAll() {
    List<CarDto> carDtos = carService.getAllDtos();
    CollectionModel<EntityModel<CarDto>> carDtoCollectionModel = CollectionModel.wrap(carDtos);
    carDtoCollectionModel.add(linkTo(methodOn(CarsController.class).getAll()).withSelfRel());

    return carDtoCollectionModel;
  }*/
}