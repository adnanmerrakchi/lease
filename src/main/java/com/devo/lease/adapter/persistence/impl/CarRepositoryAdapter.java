package com.devo.lease.adapter.persistence.impl;

import com.devo.lease.adapter.persistence.mapper.JpaMappers;
import com.devo.lease.adapter.persistence.repository.CarJpaRepository;
import com.devo.lease.domain.model.Car;
import com.devo.lease.domain.model.value.CarId;
import com.devo.lease.domain.ports.CarRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CarRepositoryAdapter implements CarRepository {

    private final CarJpaRepository carRepo;

    public CarRepositoryAdapter(CarJpaRepository carRepo) {
        this.carRepo = carRepo;
    }

    @Override
    public Optional<Car> findById(CarId id) {
        return carRepo.findById(id.value()).map(JpaMappers::toDomain);
    }
}
