package com.devo.lease.domain.ports;

import com.devo.lease.domain.model.Car;
import com.devo.lease.domain.model.value.CarId;

import java.util.Optional;

public interface CarRepository {

    Optional<Car> findById(CarId id);

}
