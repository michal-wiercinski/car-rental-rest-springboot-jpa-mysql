package com.miwi.carrental.service;

import com.miwi.carrental.domain.entity.Location;
import com.miwi.carrental.repository.LocationDao;
import com.miwi.carrental.service.generic.GenericService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService extends GenericService<Location> {

  private final LocationDao locationDao;

  public LocationService(final LocationDao locationDao) {
    this.locationDao = locationDao;
  }

  @Override
  public List<Location> findAll() {
    return locationDao.findAll();
  }

  @Override
  public Location save(Location entity) {
    return locationDao.save(entity);
  }

  @Override
  public Optional<Location> findById(Long id) {
    return locationDao.findById(id);
  }
}