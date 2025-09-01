package com.devo.lease.adapter.persistence.repository;

import com.devo.lease.adapter.persistence.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarJpaRepository extends JpaRepository<CarEntity, UUID> {
}
