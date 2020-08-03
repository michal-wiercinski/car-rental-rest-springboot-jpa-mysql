package com.miwi.carrental.service;

import com.miwi.carrental.domain.entity.Location;
import com.miwi.carrental.exception.MyResourceNotFoundException;
import com.miwi.carrental.repository.LocationDao;
import com.miwi.carrental.repository.LocationDaoImpl;
import com.miwi.carrental.service.generic.GenericService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LocationService extends GenericService<Location> {
  private static final int RESULTS_LIMIT_FOR_AUTOCOMPLETION = 10;

  private final LocationDao locationDao;
  private final LocationDaoImpl locationDaoImpl;

  public LocationService(final LocationDao locationDao,
      LocationDaoImpl locationDaoImpl) {
    this.locationDao = locationDao;
    this.locationDaoImpl = locationDaoImpl;
  }

  public List<Location> findBy(String param) {
    try {
      return checkFound(
          locationDaoImpl.searchByNameOrStreetOrCityOrZipCode(param, RESULTS_LIMIT_FOR_AUTOCOMPLETION));
    } catch (MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The location with element: " + param + " was not found",
          ex);
    }
  }
  public List<Location> findByNameOrStreetOrCityOrZipCode(String param) {
    try {
      return checkFound(
          locationDaoImpl.searchByNameOrStreetOrCityOrZipCode(param, RESULTS_LIMIT_FOR_AUTOCOMPLETION));
    } catch (MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The location with element: " + param + " was not found",
          ex);
    }
  }

  @Override
  public Location findById(Long id) {
    try {
      return checkFound(locationDao.findById(id));
    } catch (MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The location with id: " + id + " was not found");
    }
  }

  @Override
  public List<Location> findAll() {
    return locationDao.findAll();
  }

  @Override
  public Location save(Location entity) {
    return locationDao.save(entity);
  }
}