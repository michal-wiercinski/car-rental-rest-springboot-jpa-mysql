package com.miwi.carrental.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToOne;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import lombok.Data;

@NamedStoredProcedureQueries(
    @NamedStoredProcedureQuery(name = "Rental.UpdateStatus",
        procedureName = "update_rental_status_by_pk",
        parameters = {
            @StoredProcedureParameter(name = "p_pk_rental", mode = ParameterMode.IN, type = Long.class)
        }
    )
)
@Data
@Table(name = "rental")
@Entity
public class Rental {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "PK_rental")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "FK_car")
  private Car car;

  @ManyToOne
  @JoinColumn(name = "FK_user")
  private User user;

  @ManyToOne
  @JoinColumn(name = "FK_status")
  private RentalStatus rentalStatus;

  @OneToOne
  @JoinColumn(name = "FK_rental_details")
  private RentalDetails rentalDetails;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Car getCar() {
    return car;
  }

  public void setCar(Car car) {
    this.car = car;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public RentalStatus getRentalStatus() {
    return rentalStatus;
  }

  public void setRentalStatus(RentalStatus rentalStatus) {
    this.rentalStatus = rentalStatus;
  }


  public RentalDetails getRentalDetails() {
    return rentalDetails;
  }

  public void setRentalDetails(RentalDetails rentalDetails) {
    this.rentalDetails = rentalDetails;
  }
}