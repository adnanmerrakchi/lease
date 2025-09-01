package com.devo.lease.application.command;


import java.time.LocalDateTime;
import java.util.UUID;

public record LeaseCarCommand(UUID carId, UUID customerId, LocalDateTime startAt, LocalDateTime expectedReturnAt) {
}
