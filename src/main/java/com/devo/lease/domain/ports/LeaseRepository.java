package com.devo.lease.domain.ports;

import com.devo.lease.domain.model.Lease;
import com.devo.lease.domain.model.value.CarId;
import com.devo.lease.domain.model.value.LeaseId;

import java.util.Optional;

public interface LeaseRepository {
    Lease save(Lease lease);
    Optional<Lease> findActiveByCarId(CarId carId);
    Optional<Lease> findById(LeaseId id);
}
