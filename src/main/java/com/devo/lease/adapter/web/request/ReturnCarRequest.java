package com.devo.lease.adapter.web.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReturnCarRequest(@NotNull LocalDateTime returnedAt) {}