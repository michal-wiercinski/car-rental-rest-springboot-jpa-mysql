package com.miwi.carrental.models.entity;

import com.miwi.carrental.models.enums.ERentalStatus;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "rental_status")
@Entity
public class RentalStatus {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "PK_status")
  private Long id;

  @Column(name = "status_desc")
  private ERentalStatus rentalStatusType;

  @OneToMany(mappedBy = "rentalStatus")
  private List<Rental> rentals;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ERentalStatus getRentalStatusType() {
    return rentalStatusType;
  }

  public void setRentalStatusType(ERentalStatus rentalStatusType) {
    this.rentalStatusType = rentalStatusType;
  }

  public List<Rental> getRentals() {
    return rentals;
  }

  public void setRentals(List<Rental> rentals) {
    this.rentals = rentals;
  }
}