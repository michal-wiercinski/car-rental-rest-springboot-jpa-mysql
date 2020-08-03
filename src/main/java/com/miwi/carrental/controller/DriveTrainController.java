package com.miwi.carrental.controller;

import com.miwi.carrental.domain.entity.DriveTrain;
import com.miwi.carrental.service.DriveTrainService;
import java.util.List;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/driveTrain", produces = MediaTypes.HAL_JSON_VALUE)
public class DriveTrainController {

  private final DriveTrainService driveTrainService;

  public DriveTrainController(DriveTrainService driveTrainService) {
    this.driveTrainService = driveTrainService;
  }

  @GetMapping
  public ResponseEntity<List<DriveTrain>> getAll() {
    return ResponseEntity.ok().body(driveTrainService.findAll());
  }
}
