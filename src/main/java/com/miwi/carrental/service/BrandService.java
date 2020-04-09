package com.miwi.carrental.service;

import com.miwi.carrental.domain.entity.Brand;
import com.miwi.carrental.repository.BrandDao;
import com.miwi.carrental.service.generic.GenericService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandService extends GenericService<Brand> {

  private final BrandDao brandDao;

  public BrandService(final BrandDao brandDao) {
    this.brandDao = brandDao;
  }

  @Override
  public Optional<Brand> findById(Long id) {
    return brandDao.findById(id);
  }
}
