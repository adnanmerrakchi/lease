package com.devo.lease.adapter.persistence.impl;

import com.devo.lease.adapter.persistence.entity.LeaseEntity;
import com.devo.lease.adapter.persistence.mapper.JpaMappers;
import com.devo.lease.adapter.persistence.repository.LeaseJpaRepository;
import com.devo.lease.domain.model.Lease;
import com.devo.lease.domain.model.value.CarId;
import com.devo.lease.domain.model.value.LeaseId;
import com.devo.lease.domain.ports.LeaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LeaseRepositoryAdapter implements LeaseRepository {

    private final LeaseJpaRepository leaseRepo;

    public LeaseRepositoryAdapter(LeaseJpaRepository leaseRepo) {
        this.leaseRepo = leaseRepo;
    }

    @Override
    public Lease save(Lease lease) {
        LeaseEntity saved = leaseRepo.save(JpaMappers.toEntity(lease));
        return JpaMappers.toDomain(saved);
    }

    @Override
    public Optional<Lease> findActiveByCarId(CarId carId) {
        return leaseRepo.findByCarIdAndStatus(carId.value(), Lease.Status.ACTIVE.name())
                        .map(JpaMappers::toDomain);
    }

    @Override
    public Optional<Lease> findById(LeaseId id) {
        return leaseRepo.findById(id.value())
                        .map(JpaMappers::toDomain);
    }
}
