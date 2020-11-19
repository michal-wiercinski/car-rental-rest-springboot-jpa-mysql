package com.miwi.carrental.models.entity;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@NamedQueries({
    @NamedQuery(name = "Car.findRentedCarByCarId",
        query = "SELECT c.id FROM Car c JOIN Rental r ON c.id = r.car.id "
            + "WHERE c.id = :carId AND r.rentalStatus.rentalStatusType = :rentalStatus")
})
@Table(name = "car")
@Entity
public class Car implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PK_car")
  private Long id;

  @Column(name = "registration_number")
  private String registrationNumber;

  @ManyToOne
  @JoinColumn(name = "FK_location")
  private Location location;

  @ManyToOne
  @JoinColumn(name = "FK_car_model")
  private CarModel carModel;

  @ManyToOne
  @JoinColumn(name = "FK_car_status")
  private CarStatus carStatus;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "FK_car_parameter")
  private CarParameter carParameter;

  @OneToMany(mappedBy = "car",
      cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private List<Rental> rental;


  public String getRegistrationNumber() {
    return registrationNumber;
  }

  public void setRegistrationNumber(String registrationNumber) {
    this.registrationNumber = registrationNumber;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public CarModel getCarModel() {
    return carModel;
  }

  public void setCarModel(CarModel carModel) {
    this.carModel = carModel;
  }

  public CarStatus getCarStatus() {
    return carStatus;
  }

  public void setCarStatus(CarStatus carStatus) {
    this.carStatus = carStatus;
  }

  public CarParameter getCarParameter() {
    return carParameter;
  }

  public void setCarParameter(CarParameter carParameter) {
    this.carParameter = carParameter;
  }

  public List<Rental> getRental() {
    return rental;
  }

  public void setRental(List<Rental> rental) {
    this.rental = rental;
  }
}
