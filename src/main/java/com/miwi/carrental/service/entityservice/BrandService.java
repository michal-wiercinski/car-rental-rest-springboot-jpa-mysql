package com.miwi.carrental.service.entityservice;

import com.miwi.carrental.domain.entity.Brand;
import com.miwi.carrental.repository.dao.BrandDao;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandService {

  private final BrandDao brandDao;

  public BrandService(final BrandDao brandDao) {
    this.brandDao = brandDao;
  }

  public Optional<Brand> findById(Long id) {
    return brandDao.findById(id);
  }
}
