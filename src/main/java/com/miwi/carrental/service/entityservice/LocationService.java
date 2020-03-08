package com.miwi.carrental.service.entityservice;

import com.miwi.carrental.domain.entity.Location;
import com.miwi.carrental.repository.dao.LocationDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService implements IGenericService<Location> {

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
    return null;
  }

  @Override
  public Optional<Location> findById(Long id) {
    return locationDao.findById(id);
  }

  @Override
  public void delete(Location entity) {

  }

  @Override
  public void deleteById(Long id) {

  }
}