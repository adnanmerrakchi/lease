package com.devo.lease.adapter.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="lease")
public class LeaseEntity {
    @Id
    private UUID id;
    @Column(name="car_id", nullable=false)
    private UUID carId;
    @Column(name="customer_id", nullable=false)
    private UUID customerId;
    @Column(nullable=false)
    private LocalDateTime startAt;
    @Column(nullable=false)
    private LocalDateTime expectedReturnAt;
    private LocalDateTime returnedAt;
    @Column(nullable=false)
    private String status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCarId() {
        return carId;
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getExpectedReturnAt() {
        return expectedReturnAt;
    }

    public void setExpectedReturnAt(LocalDateTime expectedReturnAt) {
        this.expectedReturnAt = expectedReturnAt;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}