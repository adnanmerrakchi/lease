package com.devo.lease.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReturnReceiptDTO(UUID leaseId, UUID carId, UUID customerId, LocalDateTime returnedAt, double rentPrice) {}
