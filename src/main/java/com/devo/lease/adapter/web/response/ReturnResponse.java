package com.devo.lease.adapter.web.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReturnResponse(UUID leaseId,
                             UUID carId,
                             UUID customerId,
                             LocalDateTime returnedAt,
                             double rentPrice) {}


