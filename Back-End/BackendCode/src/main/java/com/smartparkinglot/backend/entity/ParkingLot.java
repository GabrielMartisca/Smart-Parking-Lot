package com.smartparkinglot.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "parking_lots")
public class ParkingLot {
    @Id
    @Column(name = "id", length = 200)
    private String id;

    @Column(name = "nr_spots")
    private Long nrSpots;

    @Getter @Setter
    @Column(name = "price")
    private Long price;

    @Column(name = "latitude", precision=10, scale=8)
    private BigDecimal latitude;

    @Column(name = "longitude", precision=10, scale=8)
    private BigDecimal longitude;

    public ParkingLot() {
    }

    public ParkingLot(String id, Long nrSpots, Long price, BigDecimal latitude, BigDecimal longitude) {
        this.id = id;
        this.nrSpots = nrSpots;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public BigDecimal getLatitude() {
        return latitude;
    }

    public Long getNrSpots() {
        return nrSpots;
    }

    public void setNrSpots(Long nrSpots) {
        this.nrSpots = nrSpots;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}
