package com.miwi.carrental.web;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.miwi.carrental.control.dto.RentalDto;
import com.miwi.carrental.control.mapper.dto.RentalDtoMapper;
import com.miwi.carrental.control.service.rent.RentalDetailsService;
import com.miwi.carrental.control.service.rent.RentalService;
import com.miwi.carrental.control.service.user.UserService;
import com.miwi.carrental.models.entity.Rental;
import java.net.URI;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/rentals", produces = MediaTypes.HAL_JSON_VALUE)
@CrossOrigin(origins = "*")
public class RentalController {

  private final RentalService rentalService;
  private final RentalDetailsService rentalDetailService;
  private final RentalDtoMapper rentalDtoMapper;
  private final UserService userService;
  private PagedResourcesAssembler<RentalDto> pagedDtoAssembler;

  public RentalController(
      final RentalService rentalService,
      final RentalDetailsService rentalDetailService,
      final RentalDtoMapper rentalDtoMapper,
      final UserService userService,
      final PagedResourcesAssembler<RentalDto> pagedDtoAssembler) {
    this.rentalService = rentalService;
    this.rentalDetailService = rentalDetailService;
    this.rentalDtoMapper = rentalDtoMapper;
    this.userService = userService;
    this.pagedDtoAssembler = pagedDtoAssembler;
  }

  @GetMapping("/my-rent")
  public ResponseEntity<Page<RentalDto>> getMyRent(
      Pageable pageable, Sort sort, HttpServletRequest httpServletRequest) {

    Principal principal = httpServletRequest.getUserPrincipal();

    Page<Rental> rentals = rentalService
        .getAllByUserEmail(principal.getName(),
            PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
    Page<RentalDto> rentalsDto = rentalDtoMapper.mapEntityPageToPageDto(rentals);

    return ResponseEntity.ok().body(rentalsDto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<RentalDto> getRentalById(@PathVariable("id") Long id) {

    RentalDto rentalDto = rentalDtoMapper.mapEntityToDto(rentalService.findById(id));
    rentalDto.add(
        linkTo(methodOn(RentalController.class).getMyRent(null, null, null)).withRel("myRent"));

    return ResponseEntity.ok().body(rentalDto);
  }

  @GetMapping("/all-rent")
  public ResponseEntity<Page<RentalDto>> getAllRental(
      Pageable pageable, Sort sort) {

    Page<Rental> rentals = rentalService
        .getAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
    Page<RentalDto> rentalsDto = rentalDtoMapper.mapEntityPageToPageDto(rentals);

    return ResponseEntity.ok().body(rentalsDto);
  }

  @PostMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Rental> rentFormByCarId(@RequestBody RentalDto rentalDto,
      @PathVariable("id") Long carId,
      HttpServletRequest servletRequest) {

    if (carId == null || carId < 0) {
      return ResponseEntity.badRequest().build();
    }

    Principal principal = servletRequest.getUserPrincipal();
    Rental rental = rentalService.createRental(rentalDto, carId, principal.getName());

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(rental.getId())
        .toUri();

    return ResponseEntity.created(location).build();
  }

  @PatchMapping(path = "/finish-rent/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> finishRent(@PathVariable("id") Long rentalId) {
    if (rentalId == null || rentalId < 0) {
      return ResponseEntity.badRequest().build();
    }
    rentalService.finishRental(rentalId);

    return ResponseEntity.ok().build();
  }

  @PatchMapping(path = "/cancel-rent/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> cancelRent(@PathVariable("id") Long rentalId) {
    if (rentalId == null || rentalId < 0) {
      return ResponseEntity.badRequest().build();
    }
    rentalService.cancelRent(rentalId);

    return ResponseEntity.ok().build();
  }

  private PagedModel<EntityModel<RentalDto>> getPagedModelByDto(Page<RentalDto> rentalsDto) {
    PagedModel<EntityModel<RentalDto>> rentalDtoPagedModel = pagedDtoAssembler
        .toModel(rentalsDto);

    rentalDtoPagedModel.getContent()
        .forEach(
            rentalDtoEntityModel -> rentalDtoEntityModel.add(linkTo(methodOn(RentalController.class)
                .getRentalById(rentalDtoEntityModel.getContent().getId())).withRel("rental")));

    return rentalDtoPagedModel;
  }
}
