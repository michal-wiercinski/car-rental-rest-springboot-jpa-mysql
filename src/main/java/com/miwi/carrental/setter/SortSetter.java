package com.miwi.carrental.setter;

import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class SortSetter {

  public static Sort setSort(Optional<String> sortingAttribute, Optional<String> direction) {
    if (sortingAttribute.isPresent()) {
      if (direction.isPresent() && direction.get().equals(Direction.DESC)) {
        return Sort.by(Direction.DESC, sortingAttribute.get());
      }
      return Sort.by(Direction.ASC, sortingAttribute.get());
    }
    return Sort.unsorted();
  }
}
