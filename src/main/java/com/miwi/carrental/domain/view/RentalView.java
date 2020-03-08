package com.miwi.carrental.domain.view;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@Data
@Entity
@Table(name = "rental_view")
public class RentalView {

  @Id
  @Column(name = "rental_id")
  private Long rentalId;

  @Column(name = "user_email")
  private String userEmail;

  @Column(name = "registration_number")
  private String registrationNumber;

  @Column(name = "car_id")
  private Long carId;

  @Column(name = "rental_status")
  private String rentalStatus;

  @Column(name = "brand_name")
  private String brandName;

  @Column(name = "model_name")
  private String carModel;

  @Column(name = "daily_rate")
  private Integer dailyRate;

  @Column(name = "start_date")
  private Timestamp startDate;

  @Column(name = "end_Date")
  private Timestamp endDate;

  @Column(name = "distance")
  private Integer distance;

  @Column(name = "rental_cost")
  private BigDecimal rentalCost;

  public Long getRentalId() {
    return rentalId;
  }

  public void setRentalId(Long rentalId) {
    this.rentalId = rentalId;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public String getRegistrationNumber() {
    return registrationNumber;
  }

  public void setRegistrationNumber(String registrationNumber) {
    this.registrationNumber = registrationNumber;
  }

  public Long getCarId() {
    return carId;
  }

  public void setCarId(Long carId) {
    this.carId = carId;
  }

  public String getRentalStatus() {
    return rentalStatus;
  }

  public void setRentalStatus(String rentalStatus) {
    this.rentalStatus = rentalStatus;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public Integer getDailyRate() {
    return dailyRate;
  }

  public void setDailyRate(Integer dailyRate) {
    this.dailyRate = dailyRate;
  }

  public Timestamp getStartDate() {
    return startDate;
  }

  public void setStartDate(Timestamp dateFrom) {
    this.startDate = dateFrom;
  }

  public Timestamp getEndDate() {
    return endDate;
  }

  public void setEndDate(Timestamp dateEnd) {
    this.endDate = dateEnd;
  }

  public String getCarModel() {
    return carModel;
  }

  public void setCarModel(String carModel) {
    this.carModel = carModel;
  }

  public Integer getDistance() {
    return distance;
  }

  public void setDistance(Integer distance) {
    this.distance = distance;
  }

  public BigDecimal getRentalCost() {
    return rentalCost;
  }

  public void setRentalCost(BigDecimal rentalCost) {
    this.rentalCost = rentalCost;
  }
}