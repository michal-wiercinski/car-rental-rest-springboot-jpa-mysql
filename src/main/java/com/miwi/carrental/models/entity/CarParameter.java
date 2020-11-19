package com.miwi.carrental.models.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.Data;

@NamedQueries({
    @NamedQuery(name = "CarParameter.findCarParametersByRentalDetailsId",
        query = "SELECT cp.dailyRate FROM CarParameter cp "
            + "JOIN Car c ON cp.id = c.carParameter.id "
            + "JOIN Rental r ON c.id = r.car.id "
            + "JOIN RentalDetails rd ON rd.id = r.rentalDetails.id "
            + "WHERE rd.id = :param")
})
@Table(name = "car_parameter")
@Entity
public class CarParameter {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PK_car_parameter")
  private Long id;

  @Column(name = "year_of_prod")
  private Integer yearOfProd;

  @Column(name = "daily_rate")
  private Double dailyRate;

  @Column(name = "color")
  private String color;

  @Column(name = "current_mileage")
  private Integer currentMileage;

  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(name = "FK_engine")
  private Engine engine;

  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(name = "FK_drive_train")
  private DriveTrain driveTrain;

  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(name = "FK_body_type")
  private BodyType bodyType;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getYearOfProd() {
    return yearOfProd;
  }

  public void setYearOfProd(Integer yearOfProd) {
    this.yearOfProd = yearOfProd;
  }

  public Double getDailyRate() {
    return dailyRate;
  }

  public void setDailyRate(Double dailyRate) {
    this.dailyRate = dailyRate;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public Integer getCurrentMileage() {
    return currentMileage;
  }

  public void setCurrentMileage(Integer currentMileage) {
    this.currentMileage = currentMileage;
  }

  public BodyType getBodyType() {
    return bodyType;
  }

  public void setBodyType(BodyType bodyType) {
    this.bodyType = bodyType;
  }

  public Engine getEngine() {
    return engine;
  }

  public void setEngine(Engine engine) {
    this.engine = engine;
  }

  public DriveTrain getDriveTrain() {
    return driveTrain;
  }

  public void setDriveTrain(DriveTrain driveTrain) {
    this.driveTrain = driveTrain;
  }
}