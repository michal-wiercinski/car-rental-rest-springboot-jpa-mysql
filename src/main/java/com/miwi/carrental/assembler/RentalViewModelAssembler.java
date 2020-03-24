package com.miwi.carrental.assembler;

import com.miwi.carrental.domain.view.RentalView;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class RentalViewModelAssembler implements
    RepresentationModelAssembler<RentalView, EntityModel<RentalView>> {

  @Override
  public EntityModel<RentalView> toModel(RentalView entity) {
    return new EntityModel<>(entity);
  }

  @Override
  public CollectionModel<EntityModel<RentalView>> toCollectionModel(
      Iterable<? extends RentalView> entities) {
    return new CollectionModel<>(
        StreamSupport.stream(entities.spliterator(), false).map(this::toModel).collect(
            Collectors.toList()));
  }
}