package com.miwi.carrental.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.miwi.carrental.control.dto.CarDto;
import com.miwi.carrental.control.mapper.dto.CarDtoMapper;
import com.miwi.carrental.control.service.car.CarService;
import com.miwi.carrental.domain.entity.Car;
import com.querydsl.core.types.Predicate;
import java.net.URI;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin
@RestController
@RequestMapping(path = "/cars", produces = MediaTypes.HAL_JSON_VALUE)
public class CarController {

  private Logger logger = LoggerFactory.getLogger(getClass().getName());

  private final CarService carService;
  private final CarDtoMapper carDtoMapper;

  private PagedResourcesAssembler<CarDto> pagedDtoAssembler;

  public CarController(
      final CarService carService,
      CarDtoMapper carDtoMapper,
      PagedResourcesAssembler<CarDto> pagedDtoAssembler) {
    this.carService = carService;
    this.carDtoMapper = carDtoMapper;
    this.pagedDtoAssembler = pagedDtoAssembler;
  }

  @GetMapping("/search")
  public ResponseEntity<PagedModel<EntityModel<CarDto>>> readAllCarsByFilters(Pageable pageable,
      @QuerydslPredicate(root = Car.class) Predicate predicate, Sort sort) {

    Page<Car> carPage = carService
        .findByPredicate(predicate,
            PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
    return getPagedModelResponseEntity(carPage);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Car> createCar(@Valid @RequestBody CarDto car,
      BindingResult bindingResult) {
    if (checkErrors(bindingResult)) {
      return ResponseEntity.badRequest().build();
    }

    Car newCar = carService.createNewCar(car);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(newCar.getId())
        .toUri();

    return ResponseEntity.created(location).build();
  }

  @GetMapping
  public ResponseEntity<PagedModel<EntityModel<CarDto>>> readAllCars(Pageable pageable, Sort sort) {
    Page<Car> carPage = carService
        .findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
    return getPagedModelResponseEntity(carPage);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CarDto> readCarById(@PathVariable("id") Long id) {
    Car car = carService.findById(id);
    CarDto carDto = carDtoMapper.mapEntityToDto(car);
    carDto.add(
        linkTo(methodOn(CarController.class)
            .readAllCars(null, null))
            .withRel("carsPage"));

    return ResponseEntity.ok().body(carDto);
  }

  @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CarDto> updateCar(@Valid @RequestBody CarDto carDto,
      @PathVariable("id") Long id,
      BindingResult bindingResult) {
    if (checkErrors(bindingResult)) {
      return ResponseEntity.badRequest().build();
    }
    carService.editCar(id, carDto);
    return ResponseEntity.ok().body(carDto);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Car> deleteCarById(@PathVariable("id") Long id) {
    if (carService.findById(id) != null) {
      carService.deleteById(id);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }

  @PatchMapping(path = "/make-availability/{carId}/{status}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CarDto> makeAvailability(@PathVariable("carId") Long carId,
      @PathVariable("status") String carStatus) {
    if (carId == null || carStatus.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    Car car = carService.findById(carId);
    carService.changeToAvailable(carId, carStatus);
    CarDto carDto = carDtoMapper.mapEntityToDto(car);
    return ResponseEntity.ok().body(carDto);
  }

  @GetMapping("/carStatus")
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

  @GetMapping("/bodyType")
  private ResponseEntity<PagedModel<EntityModel<CarDto>>> getByBodyTypeName(
      Pageable pageable, Sort sort,
      @RequestParam(value = "typeName") String bodyTypeParam) {

    Page<Car> cars = carService
        .findByBodyTypeName(bodyTypeParam,
            PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
    PagedModel<EntityModel<CarDto>> carsDto = getPagedModelByDto(
        carDtoMapper.mapEntityPageToPageDto(cars));

    return ResponseEntity.ok().body(carsDto);
  }

  @GetMapping("/gearboxType")
  private ResponseEntity<PagedModel<EntityModel<CarDto>>> getByGearboxType(
      Pageable pageable, Sort sort,
      @RequestParam(value = "typeName") String gearBoxTypeParam) {

    Page<Car> cars = carService
        .findByGearboxType(gearBoxTypeParam,
            PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
    PagedModel<EntityModel<CarDto>> carsDto = getPagedModelByDto(
        carDtoMapper.mapEntityPageToPageDto(cars));

    return ResponseEntity.ok().body(carsDto);
  }

  @GetMapping("/fuelType")
  private ResponseEntity<PagedModel<EntityModel<CarDto>>> getByFuelType(
      Pageable pageable, Sort sort,
      @RequestParam(value = "typeName") String fuelTypeParam) {

    Page<Car> cars = carService
        .findByFuelType(fuelTypeParam,
            PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
    PagedModel<EntityModel<CarDto>> carsDto = getPagedModelByDto(
        carDtoMapper.mapEntityPageToPageDto(cars));

    return ResponseEntity.ok().body(carsDto);
  }

  private ResponseEntity<PagedModel<EntityModel<CarDto>>> getPagedModelResponseEntity(
      Page<Car> carPage) {
    if (carPage == null) {
      return ResponseEntity.notFound().build();
    }
    Page<CarDto> carsDto = carDtoMapper.mapEntityPageToPageDto(carPage);

    PagedModel<EntityModel<CarDto>> carsPagedModel = getPagedModelByDto(carsDto);

    return ResponseEntity.ok().body(carsPagedModel);
  }


  private PagedModel<EntityModel<CarDto>> getPagedModelByDto(Page<CarDto> carsDto) {
    PagedModel<EntityModel<CarDto>> carDtoPagedModel = pagedDtoAssembler
        .toModel(carsDto);

    carDtoPagedModel.getContent()
        .forEach(carDtoEntityModel -> carDtoEntityModel.add(linkTo(methodOn(CarController.class)
            .readCarById(carDtoEntityModel.getContent().getId())).withRel("car")));
    carDtoPagedModel
        .add(
            linkTo(methodOn(CarController.class)
                .readAllCars(null, null))
                .withRel("carsPage"));
    return carDtoPagedModel;
  }

  private boolean checkErrors(BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      System.out.println("there were errors");
      bindingResult.getAllErrors().forEach(error -> {
        String errorObjectName = error.getObjectName();
        logger.warn("Validation failed - Object '{}': {}",
            errorObjectName.substring(0, 1).toUpperCase() + errorObjectName.substring(1),
            error.getDefaultMessage());
      });
      return true;
    }
    return false;
  }
}
