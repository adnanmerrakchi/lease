package com.devo.lease.adapter.web.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record LeaseResponse(UUID leaseId,
                            UUID carId,
                            UUID customerId,
                            LocalDateTime startAt,
                            LocalDateTime expectedReturnAt) {}
