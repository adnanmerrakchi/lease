package com.devo.lease.application.command;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReturnCarCommand(UUID leaseId, LocalDateTime returnedAt) {
}
