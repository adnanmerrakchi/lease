package com.devo.lease.adapter.persistence.repository;

import com.devo.lease.adapter.persistence.entity.LeaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LeaseJpaRepository extends JpaRepository<LeaseEntity, UUID> {

    Optional<LeaseEntity> findByCarIdAndStatus(UUID carId, String status);

}
