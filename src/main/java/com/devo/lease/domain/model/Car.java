package com.devo.lease.domain.model;

import com.devo.lease.domain.model.value.CarId;

public final class Car {
    private final CarId id;
    private final String vin;
    private final String make;
    private final String model;
    private final int year;
    private final double rentPrice;
    public Car(CarId id, String vin, String make, String model, int year, double rentPrice) {
        if (vin == null || vin.isBlank()) throw new IllegalArgumentException("VIN required");
        this.id = id; this.vin = vin; this.make = make; this.model = model; this.year = year; this.rentPrice = rentPrice;
    }
    public CarId id() { return id; }
    public double rentPrice() { return rentPrice; }
}