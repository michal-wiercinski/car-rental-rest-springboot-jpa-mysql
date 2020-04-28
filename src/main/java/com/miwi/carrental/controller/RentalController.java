package com.miwi.carrental.controller;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.miwi.carrental.domain.dto.RentalDto;
import com.miwi.carrental.domain.entity.Rental;
import com.miwi.carrental.domain.entity.RentalDetails;
import com.miwi.carrental.mapper.dto.RentalDtoMapper;
import com.miwi.carrental.security.service.UserService;
import com.miwi.carrental.service.RentalDetailService;
import com.miwi.carrental.service.RentalService;
import java.net.URI;
import java.security.Principal;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping(value = "/rent-car", produces = MediaTypes.HAL_JSON_VALUE)
@CrossOrigin(origins = "*")
public class RentalController {

  private final RentalService rentalService;
  private final RentalDetailService rentalDetailService;
  private final RentalDtoMapper rentalDtoMapper;
  private final UserService userService;
  private PagedResourcesAssembler<RentalDto> pagedDtoAssembler;

  public RentalController(
      final RentalService rentalService,
      final RentalDetailService rentalDetailService,
      RentalDtoMapper rentalDtoMapper, final UserService userService) {
    this.rentalService = rentalService;
    this.rentalDetailService = rentalDetailService;
    this.rentalDtoMapper = rentalDtoMapper;
    this.userService = userService;
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
      Pageable pageable, Sort sort, HttpServletRequest httpServletRequest) {

    Page<Rental> rentals = rentalService
        .getAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
    Page<RentalDto> rentalsDto = rentalDtoMapper.mapEntityPageToPageDto(rentals);

    return ResponseEntity.ok().body(rentalsDto);
  }

  @PostMapping(path = "create/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Rental> rentFormById(@RequestBody Rental rental,
      @PathVariable("id") Long carId,
      HttpServletRequest servletRequest) {

    if (carId == null || carId < 0) {
      return ResponseEntity.badRequest().build();
    }

    Principal principal = servletRequest.getUserPrincipal();
    rentalService.createRental(rental, carId, principal.getName());

    return ResponseEntity.created(URI.create("/my-rent/")).build();
  }

  @PatchMapping(path = "/cancel/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Rental> cancelRent(@PathVariable("id") Long rentalId) {
    if (rentalId == null || rentalId < 0) {
      return ResponseEntity.badRequest().build();
    }
    Rental rental = rentalService.findById(rentalId);
    RentalDetails rentalDetails = rental.getRentalDetails();

    rentalDetailService.updateDate(rentalDetails.getId());
    rentalService.updateStatus(rentalId);

    return ResponseEntity.ok().body(rental);
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

  // private void addMyRentLink()

}