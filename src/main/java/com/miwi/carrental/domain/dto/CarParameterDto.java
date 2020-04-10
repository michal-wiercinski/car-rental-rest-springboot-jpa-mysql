package com.miwi.carrental.domain.dto;

import com.miwi.carrental.domain.enums.BodyTypeName;
import com.miwi.carrental.domain.enums.FuelType;
import com.miwi.carrental.domain.enums.GearboxType;
import com.miwi.carrental.domain.enums.WheelDrive;
import com.miwi.carrental.validation.OnUpdate;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;


public class CarParameterDto {

  private Long id;

  @NotNull(message = "The daily rate can't be null", groups = OnUpdate.class)
  private Integer dailyRate;

  @NotNull(message = "The name of the body type cannot be empty", groups = OnUpdate.class)
  private String bodyTypeName;

  @NotNull(message = "The number of seats cannot be empty", groups = OnUpdate.class)
  private Integer numberOfSeats;

  @NotNull(message = "The number of doors cannot be empty", groups = OnUpdate.class)
  private Integer numberOfDoors;

  @NotNull(message = "The daily rate cannot be empty", groups = OnUpdate.class)
  private Integer fuelTankVolume;

  @NotNull(message = "You must enter the volume of luggage", groups = OnUpdate.class)
  private Integer volumeOfLuggage;

  @NotNull(message = "The engine size can't be null", groups = OnUpdate.class)
  @Range(min = 1000, max = 10000, message = "The engine size must have a minimum of 1000 and a maximum of 10000 cm3")
  private Integer engineCapacity;

  @NotNull(message = "The engine size can't be null", groups = OnUpdate.class)
  @Range(min = 50, max = 400, message = "The engine power must have a minimum of 50 and a maximum of 400 HP")
  private Integer enginePower;

  @NotNull(message = "You must enter the current mileage", groups = OnUpdate.class)
  private Integer currentMileage;

  @NotNull(message = "You must choose the fuel type", groups = OnUpdate.class)
  private FuelType fuelType;

  @NotNull(message = "You must enter the average fuel consumption", groups = OnUpdate.class)
  private Double averageFuelConsumption;

  @NotNull(message = "You must choose the wheel drive", groups = OnUpdate.class)
  private String wheelDrive;

  @NotNull(message = "You must choose the gearbox type", groups = OnUpdate.class)
  private String gearboxType;

  @NotNull(message = "The year of production can't be null", groups = OnUpdate.class)
  @Range(min = 1950, max = 2100, message = "The year of production must be a minimum of 1950 and a maximum of 2100")
  private Integer yearOfProd;

  @NotNull(message = "You must choose the color", groups = OnUpdate.class)
  private String color;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getDailyRate() {
    return dailyRate;
  }

  public void setDailyRate(Integer dailyRate) {
    this.dailyRate = dailyRate;
  }

  public String getBodyTypeName() {
    return bodyTypeName;
  }

  public void setBodyTypeName(String bodyTypeName) {
    this.bodyTypeName = bodyTypeName;
  }

  public Integer getNumberOfSeats() {
    return numberOfSeats;
  }

  public void setNumberOfSeats(Integer numberOfSeats) {
    this.numberOfSeats = numberOfSeats;
  }

  public Integer getNumberOfDoors() {
    return numberOfDoors;
  }

  public void setNumberOfDoors(Integer numberOfDoors) {
    this.numberOfDoors = numberOfDoors;
  }

  public Integer getFuelTankVolume() {
    return fuelTankVolume;
  }

  public void setFuelTankVolume(Integer fuelTankVolume) {
    this.fuelTankVolume = fuelTankVolume;
  }

  public Integer getVolumeOfLuggage() {
    return volumeOfLuggage;
  }

  public void setVolumeOfLuggage(Integer volumeOfLuggage) {
    this.volumeOfLuggage = volumeOfLuggage;
  }


  public Integer getEngineCapacity() {
    return engineCapacity;
  }

  public void setEngineCapacity(Integer engineCapacity) {
    this.engineCapacity = engineCapacity;
  }

  public Integer getEnginePower() {
    return enginePower;
  }

  public void setEnginePower(Integer enginePower) {
    this.enginePower = enginePower;
  }

  public Integer getCurrentMileage() {
    return currentMileage;
  }

  public void setCurrentMileage(Integer currentMileage) {
    this.currentMileage = currentMileage;
  }

  public FuelType getFuelType() {
    return fuelType;
  }

  public void setFuelType(FuelType fuelType) {
    this.fuelType = fuelType;
  }

  public Double getAverageFuelConsumption() {
    return averageFuelConsumption;
  }

  public void setAverageFuelConsumption(Double averageFuelConsumption) {
    this.averageFuelConsumption = averageFuelConsumption;
  }

  public String getWheelDrive() {
    return wheelDrive;
  }

  public void setWheelDrive(String wheelDrive) {
    this.wheelDrive = wheelDrive;
  }

  public String getGearboxType() {
    return gearboxType;
  }

  public void setGearboxType(String gearboxType) {
    this.gearboxType = gearboxType;
  }

  public Integer getYearOfProd() {
    return yearOfProd;
  }

  public void setYearOfProd(Integer yearOfProd) {
    this.yearOfProd = yearOfProd;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }
}
