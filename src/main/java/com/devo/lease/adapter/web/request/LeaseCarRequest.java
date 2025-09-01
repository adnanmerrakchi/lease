package com.devo.lease.adapter.web.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record LeaseCarRequest(
        @NotNull UUID carId,
        @NotNull UUID customerId,
        @NotNull LocalDateTime startAt,
        @NotNull LocalDateTime expectedReturnAt
){}
