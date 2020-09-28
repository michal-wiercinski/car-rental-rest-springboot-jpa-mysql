package com.miwi.carrental.models.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import lombok.Data;

@NamedStoredProcedureQueries(
    @NamedStoredProcedureQuery(name = "RentalDetail.UpdateEndTime",
        procedureName = "update_end_date_by_pk",
        parameters = {
            @StoredProcedureParameter(name = "p_pk_rental_detail", mode = ParameterMode.IN, type = Long.class)
        }
    )
)
@Table(name = "rental_details")
@Entity
public class RentalDetails {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "PK_rental_details")
  private Long id;

  @Column(name = "start_date")
  private Timestamp startDate;

  @Column(name = "end_date")
  private Timestamp endDate;

  @Column(name = "rental_cost")
  private BigDecimal rentalCost;

  @Column(name = "distance")
  private Integer distance;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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